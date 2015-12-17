package org.xourse.resource;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xourse.AccessDeniedException;
import org.xourse.entity.*;
import org.xourse.resource.info.StuRegInfo;
import org.xourse.resource.info.TeacherInfo;
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
 * Teacher resource
 * Created by Liu Yuhui on 2015/11/26.
 */
@Path("/teacher")
@Component
public class TeachersRes {

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
        List<TeacherInfo.WithDepartment> teas;
        try {
            teas = userService.findAllTeachers().stream()
                    .map(TeacherInfo.WithDepartment::new).collect(Collectors.toList());
        } catch (Exception e) {
            return MessageUtils.fail(e.getMessage());
        }
        Map<String, Object> m = MessageUtils.success();
        m.put("students", teas);
        return m;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> create(TeacherSubmit submit) {
        // null condition
        if(null == submit || null == submit.teacher) {
            return MessageUtils.fail("Invalid submit");
        }
        // profile
        if(null != submit.profile)
            submit.teacher.setTeacherProfile(submit.profile);
        else
            submit.teacher.setTeacherProfile(new TeacherProfile());
        // major class
        if(null != submit.departmentId){
            Department d = new Department();
            d.setId(submit.departmentId);
            submit.teacher.setDepartment(d);
        }
        try {
            userService.createTeacher(submit.teacher);
        } catch (Exception e) {
            return MessageUtils.fail(e.getMessage());
        }
        Map<String, Object> m = MessageUtils.success();
        m.put("teacher", new TeacherInfo.Detailed(submit.teacher));
        return m;
    }

    public static class TeacherSubmit {
        public Integer departmentId;
        @JsonUnwrapped
        public Teacher teacher;
        @JsonUnwrapped
        public TeacherProfile profile;
    }

    @Path("/self")
    public TeacherRes doSelf() {
        User u = SessionUtils.getUser(request);
        if(null == u) {
            throw new AccessDeniedException("Unable to access this student resource, user not logged in");
        }
        return new TeacherRes(u.getId());
    }

    @Path("{id : \\d+}")
    public TeacherRes doPath(@PathParam("id")String id) {
        return new TeacherRes(Integer.valueOf(id));
    }

    public class TeacherRes {
        private int id;

        public TeacherRes(int id) {
            this.id = id;
        }

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> get() {
            Teacher t;
            try {
                t = (Teacher) userService.findUser(id);
            } catch (Exception e) {
                return MessageUtils.fail(e.getMessage());
            }

            if(null == t) {
                return MessageUtils.fail("teacher not found");
            }
            Map<String, Object> m = MessageUtils.success();
            m.put("user", new TeacherInfo.Detailed(t));
            return m;
        }

        @PUT
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> update(TeacherSubmit submit) {
            // null condition
            if(null == submit) return MessageUtils.fail("invalid submit");
            if(null == submit.teacher) submit.teacher = new Teacher();
            // id
            submit.teacher.setId(id);
            // profile
            if(null != submit.profile) {
                submit.teacher.setTeacherProfile(submit.profile);
            }
            // class
            if(null != submit.departmentId) {
                Department d = new Department();
                d.setId(submit.departmentId);
                submit.teacher.setDepartment(d);
            }
            Teacher tea;
            try {
                tea = userService.updateTeacher(submit.teacher);
            } catch (Exception e) {
                return MessageUtils.fail(e.getMessage());
            }
            Map<String, Object> m = MessageUtils.success();
            m.put("teacher", new TeacherInfo.Detailed(tea));
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

        /**
         * get elective courses
         * @return
         */
        @Path("/elective_courses")
        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> getElective() {
            List<CoursePlan> courses = null;
            List<ElectiveCourse> electives = null;
            boolean isModifiable = true;
            Calendar c = Calendar.getInstance();
            int nyear = c.get(Calendar.YEAR);
            String year;
            if(c.get(Calendar.MONTH) >= 6)
                year = Integer.toString(nyear) + "-" + Integer.toString(nyear + 1) + "-1";
            else
                year = Integer.toString(nyear - 1) + "-" + Integer.toString(nyear) + "-2";
            try {
                Teacher t = new Teacher(id);
                isModifiable = courseService.isTeacherModifiable(t, year);
                if(isModifiable)
                    courses = courseService.findAllElectivePlan();
                else
                    electives = courseService.findElectiveByTeacher(t);
            } catch (Exception e) {
                return MessageUtils.fail(e);
            }
            Map<String, Object> m = MessageUtils.success();
            m.put("isSubmitted", !isModifiable);
            if(isModifiable)
                m.put("courses", courses);
            else
                m.put("courses", electives);
            return m;
        }

        /**
         * save elective course selections
         * @return
         */
        @Path("/elective_courses")
        @POST
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> saveElectiveSelection(TeacherElectiveSubmit submit) {
            if(null == submit || null == submit.year)
                return MessageUtils.fail("invalid submit or year is not specified");
            try {
                Teacher t = new Teacher(id);
                courseService.teaSignForElectives(t, submit.courses, submit.year);
            } catch (Exception e) {
                return MessageUtils.fail(e);
            }
            return MessageUtils.success();
        }

        @Path("/courses/year")
        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> getCourseYears() {
            Teacher t = new Teacher(id);
            List<String> years;
            try {
                years = courseService.findAllCourseYears(t);
            } catch (Exception e) {
                return MessageUtils.fail(e);
            }
            Map<String, Object> m = MessageUtils.success();
            m.put("years", years);
            return m;
        }

        @Path("/courses/year/{year : \\d{4}-\\d{4}-[12]}")
        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> getCoursesOfYear(@PathParam("year")String year) {
            Teacher t = new Teacher(id);
            return taskRunner.run(() -> {
                Map<String, Object> m = MessageUtils.success();
                List<Course> courses = courseService.findCoursesByTeacherAndYear(t, year);
                m.put("courses", courses);
                return m;
            }, MessageUtils::fail);
        }


        @Path("/courses/{cid : \\d+}")
        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> getRegistrationsOfCourse(@PathParam("cid")String cid) {
            return taskRunner.run(() -> {
                int _cid = 0;
                try {
                    _cid = Integer.valueOf(cid);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("cid is not valid: " + cid);
                }
                Map<String, Object> m = MessageUtils.success();
                Course c = courseService.findById(_cid);
                List<CourseRegistration> regs = courseService.findRegistrations(c);
                List<StuRegInfo> regInfos = regs.stream().map(StuRegInfo::new).collect(Collectors.toList());
                m.put("course", c);
                m.put("registrations", regInfos);
                return m;
            }, MessageUtils::fail);
        }

        @Path("/courses/{cid : \\d+}")
        @POST
        @Produces(MediaType.APPLICATION_JSON)
        @Consumes(MediaType.APPLICATION_JSON)
        public Map<String, Object> postScores(@PathParam("cid")String cid, List<StuRegInfo> regs) {
            return taskRunner.run(() -> {
                int _cid = 0;
                try {
                    _cid = Integer.valueOf(cid);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("cid is not valid: " + cid);
                }

                Map<String, Object> m = MessageUtils.success();
                Course c = new Course();
                c.setId(_cid);
                courseService.setCourseFinished(c);
                courseService.updateScores(c, regs);
                return m;
            }, MessageUtils::fail);
        }
    }

    public static class TeacherElectiveSubmit {
        public String year;
        public List<Integer> courses;
    }
}
