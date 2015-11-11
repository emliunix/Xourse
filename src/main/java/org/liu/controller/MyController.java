package org.liu.controller;

import org.hibernate.SessionFactory;
import org.liu.entity.User;
import org.liu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Liu Yuhui on 2015/11/6.
 */
@RestController
public class MyController {

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    UserService userService;

    @RequestMapping(path = "test/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> handler(@PathVariable("id")int id) throws Exception {
        User user = new User("Liu", "Admin");
        Serializable se = userService.saveUser(user);
        Map<String, Object> map = new HashMap<>();
        map.put("Hello", "world!!!");
        map.put("Class", se.getClass().getSimpleName());
        map.put("Value", se.toString());
        map.put("User", user);
        if(true) throw new Exception("Hello world!!!");
        return map;
    }
}
