package org.xourse.utils;

import org.xourse.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * SessionUtils负责往session中存入user或者取出user
 * Created by Liu Yuhui on 2015/11/26.
 */
public class SessionUtils {
    private static final String SERVLET_USER_ATTRIBUTE = "org.xourse.user";

    /**
     * 将user存入当前session
     * @param user
     * @param request
     */
    public static void saveUser(User user, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.setAttribute(SERVLET_USER_ATTRIBUTE, user);
    }

    /**
     * 从当前session获取user
     * @param request
     * @return User对象
     * @throws IllegalStateException 当session中存在异常的user时抛出该异常
     */
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
