package org.xourse.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.xourse.service.UserService;

/**
 * Created by Liu Yuhui on 2015/11/26.
 */
@Component
@Scope("request")
public class AdminRes {
    @Autowired
    private UserService userService;
}
