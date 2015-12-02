package org.xourse;

import org.apache.log4j.Logger;
import org.hibernate.annotations.Check;
import org.xourse.entity.User;
import org.xourse.utils.SessionUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 检查用户在访问layout.html时是否已经登陆
 * Created by Liu Yuhui on 2015/11/26.
 */
public class CheckLoginFilter implements Filter {

    private Logger logger = Logger.getLogger(CheckLoginFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        User user = SessionUtils.getUser((HttpServletRequest)request);
        logger.info("Before filter");
        if (null == user) {
            HttpServletResponse resp = (HttpServletResponse)response;
            resp.sendRedirect(request.getServletContext().getContextPath() + "/login.html");
        } else {
            chain.doFilter(request, response);
        }
        logger.info("After filter");
    }

    @Override
    public void destroy() { }
}
