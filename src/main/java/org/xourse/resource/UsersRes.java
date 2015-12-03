package org.xourse.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xourse.AccessDeniedException;
import org.xourse.entity.Admin;
import org.xourse.entity.Student;
import org.xourse.entity.Teacher;
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
 * User resource
 * Created by Liu Yuhui on 2015/11/26.
 */
@Component
@Path("/user")
public class UsersRes {
    @Autowired
    private UserService userService;
    @Context
    private HttpServletRequest req;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getUsers() {
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

    @Path("/{id : \\d+}")
    public UserRes doUser(@PathParam("id")String id) {
        return new UserRes(Integer.valueOf(id));
    }

    @Autowired
    TeachersRes teachersRes;
    @Autowired
    StudentsRes studentsRes;
    @Autowired
    AdminsRes adminsRes;

    @Path("/self")
    public Object doPath() {
        User u = SessionUtils.getUser(req);
        if(null == u || "user".equals(u.getRole())) {
            throw new AccessDeniedException("user not logged in or is not a valid user");
        }
        // do route according to the user type
        if(u instanceof Teacher || "teacher".equals(u.getRole())) {
            return teachersRes.new TeacherRes(u.getId());
        } else if(u instanceof Student || "student".equals(u.getRole())) {
            return studentsRes.new StudentRes(u.getId());
        } else if(u instanceof Admin || "admin".equals(u.getRole())) {
            return adminsRes.new AdminRes(u.getId());
        } else {
            throw new AccessDeniedException("not a valid user");
        }
    }

    public class UserRes  {
        private int id;

        private UserRes(int id) {
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
        public Map<String, Object> update(User u) {
            User user = new User();
            user.setId(id);
            user.setUsername(u.getUsername());
            user.setPassword(u.getPassword());
            try {
                user = userService.updateUser(user);
            } catch (Exception e) {
                return MessageUtils.fail(e.getMessage());
            }
            Map<String, Object> m = MessageUtils.success();
            m.put("user", new UserInfo(user));
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
