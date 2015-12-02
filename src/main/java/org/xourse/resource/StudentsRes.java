package org.xourse.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.xourse.AccessDeniedException;
import org.xourse.entity.User;
import org.xourse.service.UserService;
import org.xourse.utils.SessionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Map;

/**
 * Student Resource
 * Created by Liu Yuhui on 2015/11/26.
 */
@Component
@Path("/students")
public class StudentsRes {

    @Autowired
    private UserService userService;

    @Context
    private HttpServletRequest request;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> get() {
        return null;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> create() {
        return null;
    }

    @Path("/self")
    public StudentRes doSelf() {
        User u = SessionUtils.getUser(request);
        if(null == u) {
            throw new AccessDeniedException("Unable to access this student resource, user not logged in");
        }
        return new StudentRes(u.getId());
    }

    @Path("{id : \\d+}")
    public StudentRes doPath(@PathParam("id")String id) {
        return new StudentRes(Integer.valueOf(id));
    }

    public class StudentRes {
        private int id;

        public StudentRes(int id) {
            this.id = id;
        }

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> get() {

            return null;
        }

        @PUT
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> update() {

            return null;
        }

        @DELETE
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> delete() {

            return null;
        }
    }
}
