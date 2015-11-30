package org.xourse.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xourse.entity.Department;
import org.xourse.entity.Teacher;
import org.xourse.service.UserService;
import org.xourse.utils.MessageUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.Map;

/**
 * Created by Liu Yuhui on 2015/11/26.
 */
@Path("/teachers")
@Component
public class TeachersRes {
    @Context
    private UriInfo uriInfo;
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
    public Map<String, Object> createTeacher(TeacherInfoSubmit submit) {
        Map<String, Object> m = MessageUtils.success("success");
        Department d = new Department();
        d.setId(submit.departmentId);
        userService.createTeacher(submit.teacher, submit.profile, d);
        m.put("teacher", submit.teacher);
        return m;
    }

    @Path("/{id}/")
    public TeacherRes doTeacher(@PathParam("id")String id) {
        int _id;
        try {
            _id = Integer.valueOf(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(uriInfo.getPath());
        }
        teacherRes.setId(_id);
        return teacherRes;
    }
}
