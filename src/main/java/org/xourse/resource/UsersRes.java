package org.xourse.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.xourse.service.UserService;
import org.xourse.utils.MessageUtils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Map;

/**
 * Created by Liu Yuhui on 2015/11/26.
 */
@Path("/users")
public class UsersRes {
    @Inject
    private UserService userService;
    @Inject
    private HttpServletRequest req;

    private WebApplicationContext ctx;

    @PostConstruct
    public void initWebAppCtx() {
        ctx = WebApplicationContextUtils
                .getWebApplicationContext(req.getServletContext());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getUsers() {
        return MessageUtils.success("get users");
    }

    @Path("/{id}")
    public UserRes doUser(@PathParam("id")String id) {
        return ctx.getBean(UserRes.class, id);
    }
}
