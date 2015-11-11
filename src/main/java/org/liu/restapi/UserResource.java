package org.liu.restapi;

import org.liu.entity.User;
import org.liu.service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Liu Yuhui on 2015/11/11.
 */
public class UserResource {

    private long id;
    private UserService userService;

    public UserResource(long id, UserService userService) {
        this.id = id;
        this.userService = userService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getUser() {
        Map<String, Object> m = new HashMap<>();
        m.put("status", "ok");
        m.put("content", userService.findUser(id));
        return m;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> saveUesr(User user) {
        Map<String, Object> m = new HashMap<>();
        m.put("status", "ok");
        user.setId(id);
        userService.updateUser(user);
        return m;
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> deleteUser() {
        Map<String, Object> m = new HashMap<>();
        m.put("status", "ok");
        userService.deleteUser(id);
        return m;
    }
}
