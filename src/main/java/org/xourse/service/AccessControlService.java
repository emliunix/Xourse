package org.xourse.service;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.xourse.entity.User;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;

/**
 * Created by Liu Yuhui on 2015/11/26.
 */
@Component
@Transactional
public class AccessControlService {

    public static final String ROLE_STUDENT = "student";
    public static final String ROLE_TEACHER = "teacher";
    public static final String ROLE_ADMIN = "admin";

    @Autowired
    private UserService userService;

    public User authenticate(String username, String password) {
        try {
            if(Strings.isNullOrEmpty(password) ||
                    Strings.isNullOrEmpty(username))
                return null;

            User user = userService.findUserByName(username);
            if(null == user)
                return null;

            return password.equals(user.getPassword()) ? user : null;
        } catch (PersistenceException e) {
            return null;
        }
    }

    public boolean canAccess(String userid, String ... roles) {
        Preconditions.checkNotNull(userid);
        Preconditions.checkArgument(null != roles && roles.length > 0);
        try {
            User user = userService.findUser(userid);
            return canAccess(user, roles);
        } catch (EntityNotFoundException e) {
            return false;
        }
    }

    public boolean canAccess(User user, String ... roles) {
        Preconditions.checkNotNull(user, "user is null");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(user.getRole()), "User without a role declaration: %s", user);
        Preconditions.checkArgument(null != roles && roles.length > 0, "roles to match is null or empty");

        String userRole = user.getRole();
        for(String role : roles)
            if(userRole.equals(role))
                return true;
        return false;
    }
}
