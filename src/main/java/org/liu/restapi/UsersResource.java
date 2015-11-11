package org.liu.restapi;

import org.apache.log4j.Logger;
import org.liu.entity.User;
import org.liu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Liu Yuhui on 2015/11/7.
 */
@Path("users")
public class UsersResource {

    private static Logger logger = Logger.getLogger(UsersResource.class);

    @Autowired
    UserService userService;

    public UsersResource() {
        logger.info("Set a breakpoint in the cstor to see how this class is instantiated");
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> addUser(User user, @Context UriInfo uriInfo, @Context HttpServletRequest request) {
        Map<String, Object> m = new HashMap<>();
        m.put("status", "ok");
        Serializable se = userService.saveUser(user);
        m.put("content", user);
        return m;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getUsers() {
        Map<String, Object> m = new HashMap<>();
        m.put("content", userService.getUsers());
        return m;
    }

    @Path("{userid}")
    public UserResource doUser(@PathParam("userid") String userid) {
        long userId = Long.valueOf(userid);
        return new UserResource(userId, userService);
    }
}
