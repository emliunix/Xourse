package org.xourse.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xourse.entity.Teacher;
import org.xourse.service.UserService;
import org.xourse.utils.MessageUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

/**
 * Created by Liu Yuhui on 2015/11/26.
 */
@Path("/teachers")
@Component
public class TeachersRes {
    @Autowired
    private UserService userService;

    @Autowired
    private TeacherRes teacherRes;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> retrieveTeachers() {
        List<Teacher> teachers = userService.findAllTeachers();
        if(null == teachers)
            return MessageUtils.fail("failed to get teachers");

        Map<String, Object> m = MessageUtils.success("got teachers");
        m.put("teachers", teachers);

        return m;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> createTeacher(TeacherPostSubmit submit) {
        Map<String, Object> m = MessageUtils.success("success");
        Teacher teacher = submit.getTeacher();
        userService.saveUser(teacher);
        m.put("teacher", teacher);
        return m;
    }

    private static class TeacherPostSubmit {
        Teacher teacher;
        Long departmentId;

        public Teacher getTeacher() {
            return teacher;
        }

        public void setTeacher(Teacher teacher) {
            this.teacher = teacher;
        }

        public Long getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(Long departmentId) {
            this.departmentId = departmentId;
        }
    }

    @Path("/{id}/")
    public TeacherRes doTeacher(@PathParam("id")String id) {
        teacherRes.setId(id);
        return teacherRes;
    }
}
