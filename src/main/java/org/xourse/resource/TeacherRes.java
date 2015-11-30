package org.xourse.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.xourse.entity.Teacher;
import org.xourse.entity.User;
import org.xourse.service.UserService;
import org.xourse.utils.MessageUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Liu Yuhui on 2015/11/26.
 */
@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TeacherRes {

    @Autowired
    UserService userService;

    private Integer id;

    public void setId(Integer id) {
        this.id = id;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> retrieve() {
        Map<String, Object> m = null;

        // get teacher
        User user = userService.findUser(id);
        if(null == user || !(user instanceof Teacher))
            return MessageUtils.fail("User [" + id + "] does not exist or is not a Teacher");

        m = MessageUtils.success("teacher retrieved " + user);
        Teacher teacher = (Teacher) user;

        // build up response message
        m.put("teacher", teacher);
        Map<String, Object> links = new HashMap<>();
        m.put("links", links);

        return m;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> update(TeacherInfoSubmit submit) {
        if(null == submit)
            return MessageUtils.fail("invalid submit");
        submit.teacher.setId(id);
        userService.updateTeacher(submit.teacher, submit.profile);
        Map<String, Object> m = MessageUtils.success("teacher updated");
        try {
            m.put("teacher", new ObjectMapper().writeValueAsString(submit));
        } catch (JsonProcessingException e) {
            m.put("err msg", e.getMessage());
        }

        return m;
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> delete() {
        userService.deleteUser(id);
        return MessageUtils.success("teacher deleted");
    }

}
