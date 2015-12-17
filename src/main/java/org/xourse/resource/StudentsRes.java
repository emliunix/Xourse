package org.xourse.resource;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.xourse.AccessDeniedException;
import org.xourse.entity.*;
import org.xourse.resource.info.StuRegInfo;
import org.xourse.resource.info.StudentInfo;
import org.xourse.service.CourseService;
import org.xourse.service.UserService;
import org.xourse.utils.MessageUtils;
import org.xourse.utils.SessionUtils;
import org.xourse.utils.TaskRunner;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Student Resource
 * Created by Liu Yuhui on 2015/11/26.
 */
@Component
@Path("/student")
public class StudentsRes {
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
    @Context
    private HttpServletRequest request;

    private TaskRunner<Map<String, Object>> taskRunner = new TaskRunner<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> get() {
        List<StudentInfo.WithClass> stus = null;
        try {
            stus = userService.findAllStudents().stream()
                    .map(StudentInfo.WithClass::new).collect(Collectors.toList());
        } catch (Exception e) {
            return MessageUtils.fail(e.getMessage());
        }
        Map<String, Object> m = MessageUtils.success();
        m.put("students", stus);
        return m;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> create(StudentSubmit submit) {
        // null condition
        if(null == submit || null == submit.student) {
            return MessageUtils.fail("Invalid submit");
        }
        // profile
        if(null != submit.profile) submit.student.setStudentProfile(submit.profile);
        // major class
        if(null != submit.majorClassId){
            MajorClass c = new MajorClass();
            c.setId(submit.majorClassId);
            submit.student.setMajorClass(c);
        }
        try {
            userService.createStudent(submit.student);
        } catch (Exception e) {
            return MessageUtils.fail(e.getMessage());
        }
        Map<String, Object> m = MessageUtils.success();
        m.put("student", new StudentInfo.Detailed(submit.student));
        return m;
    }

    public static class StudentSubmit {
        public Integer majorClassId;
        @JsonUnwrapped
        @JsonIgnoreProperties("id")
        public Student student;
        @JsonUnwrapped
        @JsonIgnoreProperties("id")
        public StudentProfile profile;
    }

    @Path("/self")
    public StudentRes doSelf() {
        User u = SessionUtils.getUser(request);
        if(null == u) {
            throw new AccessDeniedException("Unable to access this student resource, user not logged in");
        }
        return new StudentRes(u.getId());
    }

    @Path("{id : \\d+}")
    public StudentRes doPath(@PathParam("id")String id) {
        return new StudentRes(Integer.valueOf(id));
    }

    public class StudentRes {
        private int id;

        public StudentRes(int id) {
            this.id = id;
        }

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> get() {
            Student s;
            try {
                s = (Student) userService.findUser(id);
            } catch (Exception e) {
                return MessageUtils.fail(e.getMessage());
            }

            if(null == s) {
                return MessageUtils.fail("student not found");
            }
            Map<String, Object> m = MessageUtils.success();
            m.put("user", new StudentInfo.Detailed(s));
            return m;
        }

        @PUT
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> update(StudentSubmit submit) {
            // null condition
            if(null == submit) return MessageUtils.fail("invalid submit");
            if(null == submit.student) submit.student = new Student();
            // id
            submit.student.setId(id);
            // profile
            if(null != submit.profile) {
                submit.student.setStudentProfile(submit.profile);
            }
            // class
            if(null != submit.majorClassId) {
                MajorClass c = new MajorClass();
                c.setId(submit.majorClassId);
                submit.student.setMajorClass(c);
            }
            Student stu = null;
            try {
                stu = userService.updateStudent(submit.student);
            } catch (Exception e) {
                return MessageUtils.fail(e.getMessage());
            }
            Map<String, Object> m = MessageUtils.success();
            m.put("student", new StudentInfo.Detailed(stu));
            return m;
        }

        @DELETE
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> delete() {
            try {
                userService.deleteUser(id);
            } catch (Exception e) {
                return MessageUtils.fail(e.getMessage());
            }
            return MessageUtils.success();
        }

        @Path("/elective_courses/year/{year : \\d{4}-\\d{4}-[12]}")
        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> getElectives(@PathParam("year") String year) {
            Student s = new Student(id);
            return taskRunner.run(() -> {
                Map<String, Object> m = MessageUtils.success();
                List<ElectiveCourse> ecs = courseService.findElectiveByYear(year);
                Boolean isModifiable = courseService.isStudentModifiable(s, year);
                m.put("courses", ecs);
                m.put("isModifiable", isModifiable);
                return m;
            }, MessageUtils::fail);
        }

        @Path("/elective_courses")
        @POST
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> postElective(ElectiveSubmit submit) {
            Student s = new Student(id);
            return taskRunner.run(() -> {
                Map<String, Object> m = MessageUtils.success();
                courseService.stuSignForElectives(s, submit.courses, submit.year);
                return m;
            }, MessageUtils::fail);
        }

        @Path("/courses/year")
        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> getCourseYears() {
        Student s = new Student(id);
            return taskRunner.run(() -> {
                Map<String, Object> m = MessageUtils.success();
                List<String> years = courseService.findAllCourseYears(s);
                m.put("years", years);
                return m;
            }, MessageUtils::fail);
        }

        @Path("/courses/year/{year : \\d{4}-\\d{4}-[12]}")
        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> getCoursesOfYear(@PathParam("year")String year) {
            Student s = new Student(id);
            return taskRunner.run(() -> {
                Map<String, Object> m = MessageUtils.success();
                List<CourseRegistration> regs = courseService.findRegistrationsByStudentAndYear(s, year);
                m.put("registrations", regs);
                return m;
            }, MessageUtils::fail);
        }
    }

    public static class ElectiveSubmit {
        public List<Integer> courses;
        public String year;
    }
}
