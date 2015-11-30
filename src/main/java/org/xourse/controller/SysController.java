package org.xourse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xourse.entity.Student;
import org.xourse.entity.User;
import org.xourse.service.AccessControlService;
import org.xourse.service.UserService;
import org.xourse.utils.MessageUtils;
import org.xourse.utils.SessionUtils;
import org.xourse.utils.UserIdBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Login / Logout Controller
 * Created by Liu Yuhui on 2015/11/24.
 */
@RestController
public class SysController {
    @Autowired
    private UserService userService;

    @Autowired
    private AccessControlService aclService;

    @RequestMapping(path = "/init_data")
    public Map<String, Object> initData() {
        Map<String, Object> m = null;

        Student stu = new Student();
        stu.setUsername("liu");
        stu.setPassword("pass");

        userService.saveUser(stu);

        return MessageUtils.success("added student " + stu);
    }

    @RequestMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public Map<String, Object> login(@RequestParam String username,
                                     @RequestParam String password,
                                     HttpServletRequest request) {
        User user = aclService.authenticate(username, password);
        if(null == user)
            return MessageUtils.fail("login failed");

        SessionUtils.saveUser(user, request);
        return MessageUtils.success("login with user " + user.toString());
    }

    @RequestMapping(path = "/check_user", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> check(HttpServletRequest request) {
        User user = SessionUtils.getUser(request);
        if(null == user)
            return MessageUtils.fail("user not login");
        Map<String, Object> m = MessageUtils.success("user login");
        Map<String, Object> u = new HashMap<>();
        u.put("username", user.getUsername());
        u.put("id", user.getId());
        u.put("role", user.getRole());
        m.put("user", u);
        return m;
    }

    @RequestMapping(path = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(null != session) session.invalidate();
        return MessageUtils.success("logout");
    }
}
