package org.xourse;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Index servlet that redirect user to layout.html
 * Created by Liu Yuhui on 2015/12/17.
 */
public class IndexServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/layout.html");
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        service((HttpServletRequest)req, (HttpServletResponse)res);
    }
}
