package org.xourse.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Liu Yuhui on 2015/11/6.
 */
@RestController
@ControllerAdvice(basePackages = "org.liu.controller")
public class ExceptionController {
    @ExceptionHandler(Exception.class)
    public Map<String, Object> handle(Exception exc) {
        Map<String, Object> map = new HashMap<>();
        map.put("exception", exc);
        return map;
    }
}
