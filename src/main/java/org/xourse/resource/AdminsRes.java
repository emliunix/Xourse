package org.xourse.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xourse.entity.Admin;
import org.xourse.entity.User;
import org.xourse.resource.info.UserInfo;
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
 * Created by Liu Yuhui on 2015/11/26.
 */
@Component
@Path("/admin")
public class AdminsRes {
    @Autowired
    private UserService userService;
    @Context
    private HttpServletRequest req;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> get() {
        List<UserInfo> us;
        try {
            us = userService.findAllUsers().stream()
            .map(UserInfo::new).collect(Collectors.toList());
        } catch (Exception e) {
            return MessageUtils.fail(e.getMessage());
        }
        Map<String, Object> m = MessageUtils.success();
        m.put("users", us);
        return m;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> create(Admin admin) {
        if(null == admin)
            return MessageUtils.fail("invalid submit");
        Admin a = new Admin();
        a.setUsername(admin.getUsername());
        a.setPassword(admin.getPassword());
        try {
            userService.createAdmin(a);
        } catch (Exception e) {
            return MessageUtils.fail(e.getMessage());
        }
        Map<String, Object> m = MessageUtils.success();
        m.put("admin", a);
        return m;
    }

    @Path("/{id}")
    public AdminRes doUser(@PathParam("id")String id) {
        return new AdminRes(Integer.valueOf(id));
    }

    public class AdminRes {
        private int id;

        public AdminRes(int id) {
            this.id = id;
        }

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> get() {
            User user = SessionUtils.getUser(req);
            if(null == user)
                return MessageUtils.fail("cannot get user");
            Map<String, Object> m = MessageUtils.success();
            m.put("user", new UserInfo(user));
            return m;
        }

        @PUT
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> update(Admin a) {
            Admin admin = new Admin();
            admin.setId(id);
            admin.setUsername(a.getUsername());
            admin.setPassword(a.getPassword());
            try {
                admin = userService.updateAdmin(admin);
            } catch (Exception e) {
                return MessageUtils.fail(e.getMessage());
            }
            Map<String, Object> m = MessageUtils.success();
            m.put("user", new UserInfo(admin));
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
