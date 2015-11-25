package org.xourse.utils;

import org.xourse.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Liu Yuhui on 2015/11/26.
 */
public class SessionUtils {
    private static final String SERVLET_USER_ATTRIBUTE = "org.xourse.user";

    public static void saveUser(User user, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.setAttribute(SERVLET_USER_ATTRIBUTE, user);
    }

    public static User getUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(null == session)
            return null;

        Object o = session.getAttribute(SERVLET_USER_ATTRIBUTE);
        if(!(o instanceof User))
            throw new IllegalStateException("User saved into session is not actually a User");

        return (User) o;
    }
}
