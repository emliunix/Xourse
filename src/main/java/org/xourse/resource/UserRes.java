package org.xourse.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.xourse.entity.User;
import org.xourse.service.UserService;
import org.xourse.utils.MessageUtils;
import org.xourse.utils.SessionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Map;

/**
 * Created by Liu Yuhui on 2015/11/26.
 */
@Component
@Scope("request")
public class UserRes  {
    private Integer id;
    @Autowired
    private UserService userService;
    @Autowired
    private HttpServletRequest request;

    private UserRes(Integer id) {
        this.id = id;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> get() {
        User user = SessionUtils.getUser(request);
        if(null == user)
            return MessageUtils.fail("cannot get user");
        Map<String, Object> m = MessageUtils.success("Hello world!!!");
        m.put("user", new WrappedUser(user));
        return m;
    }

    public static class WrappedUser {
        private Integer id;
        private String username;
        private String password;
        private String role;

        public WrappedUser(User user) {
            id = user.getId();
            username = user.getUsername();
            password = user.getPassword();
            role = user.getRole();
        }

        public Integer getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String getRole() {
            return role;
        }
    }
}
