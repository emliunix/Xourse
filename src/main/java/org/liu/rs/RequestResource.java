package org.liu.rs;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Liu Yuhui on 2015/11/7.
 */
public class RequestResource {
    private HttpServletRequest request;

    public RequestResource(HttpServletRequest request) {
        this.request = request;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> handle() {
        Enumeration<String> attrnames = request.getAttributeNames();
        Map<String, String> m = new HashMap<>();
        while(attrnames.hasMoreElements()) {
            String key = attrnames.nextElement();
            m.put(key, request.getAttribute(key).toString());
        }

        ServletContext ctx = request.getServletContext();
        attrnames = ctx.getAttributeNames();
        while(attrnames.hasMoreElements()) {
            String key = attrnames.nextElement();
            m.put(key, ctx.getAttribute(key).toString());
        }

        m.put("Hello", "World!!!");

        return m;
    }
}
