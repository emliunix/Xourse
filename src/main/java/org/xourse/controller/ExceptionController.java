package org.xourse.controller;

import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.xourse.utils.MessageUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Liu Yuhui on 2015/11/6.
 */
@RestController
@ControllerAdvice(basePackages = "org.xourse.controller")
public class ExceptionController {
    @ExceptionHandler(Exception.class)
    public Map<String, Object> handle(Exception exc) {
        return MessageUtils.fail(exc.getMessage());
    }

    @ExceptionHandler(DataAccessException.class)
    public Map<String, Object> daoException(DataAccessException exc) {
        return MessageUtils.fail(exc.getMessage());
    }
}
