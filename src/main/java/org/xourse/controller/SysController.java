package org.xourse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xourse.entity.Student;
import org.xourse.entity.User;
import org.xourse.service.UserService;
import org.xourse.utils.MessageUtils;
import org.xourse.utils.UserIdBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Login / Logout Controller
 * Created by Liu Yuhui on 2015/11/24.
 */
@RestController
public class SysController {
    @Autowired
    private UserService userService;

    @RequestMapping(path = "/init_data")
    public Map<String, Object> initData() {
        Map<String, Object> m = null;

        Student stu = new Student();
        stu.setUsername("Liu Yuhui");
        stu.setPassword("pass");
        String id = UserIdBuilder.studentId(24).year(2013).department(2200).major(400).build();
        stu.setId(id);
        stu.setRole("student");

        userService.saveUser(stu);

        return MessageUtils.success("added student");
    }

    @RequestMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public Map<String, Object> login(@RequestParam String username,
                                     @RequestParam String password) {
        Map<String, Object> m = null;

        User user = userService.findUserByName("Liu Yuhui");
        if(null == user) {
            user = new User();
            String userid = UserIdBuilder.studentId("023")
                    .major("0001")
                    .department("0002")
                    .year("2013")
                    .build();
            user.setId(userid);
            user.setUsername("Liu Yuhui");
            user.setPassword("adminpass");
            user.setRole("admin");
            userService.saveUser(user);
        }

        m = MessageUtils.success("logined with name");
        return m;
    }

    @RequestMapping(path = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> logout() {
        return MessageUtils.success("this is a test");
    }
}
