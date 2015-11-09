package org.liu.restapi;

import org.apache.log4j.Logger;
import org.liu.persistence.User;
import org.liu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
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
        Map<String, Object> result = new HashMap<>();
        result.put("result", user.toString());
        result.put("userService", userService.toString());
        return result;
    }
}
