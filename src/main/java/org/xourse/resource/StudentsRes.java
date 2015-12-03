package org.xourse.resource;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xourse.AccessDeniedException;
import org.xourse.entity.MajorClass;
import org.xourse.entity.Student;
import org.xourse.entity.StudentProfile;
import org.xourse.entity.User;
import org.xourse.resource.info.StudentInfo;
import org.xourse.service.UserService;
import org.xourse.utils.MessageUtils;
import org.xourse.utils.SessionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
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

    @Context
    private HttpServletRequest request;

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
        public Student student;
        @JsonUnwrapped
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
            m.put("student", new StudentInfo.Detailed(s));
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
    }
}
