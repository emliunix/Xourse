package org.xourse.resource;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xourse.AccessDeniedException;
import org.xourse.entity.Department;
import org.xourse.entity.Teacher;
import org.xourse.entity.TeacherProfile;
import org.xourse.entity.User;
import org.xourse.resource.info.TeacherInfo;
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
 * Teacher resource
 * Created by Liu Yuhui on 2015/11/26.
 */
@Path("/teacher")
@Component
public class TeachersRes {

    @Autowired
    private UserService userService;

    @Context
    private HttpServletRequest request;

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
        m.put("teacher", new TeacherInfo.WithDepartment(submit.teacher));
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
            m.put("teacher", new TeacherInfo.Detailed(t));
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
    }
}
