package org.xourse.resource.info;

import org.xourse.entity.User;

/**
 * Created by Liu Yuhui on 2015/12/2.
 */
public class UserInfo {
    public Integer id;
    public String username;
    public String role;

    public UserInfo(User user) {
        id = user.getId();
        username = user.getUsername();
        role = user.getRole();
    }
}
