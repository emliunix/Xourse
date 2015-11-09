package org.liu.rs;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Liu Yuhui on 2015/10/9.
 */
@Path("hello")
public class Hello {
    @Context
    UriInfo uriInfo;

    @Context
    HttpServletRequest request;

    private static Logger logger = Logger.getLogger(Hello.class);

    public Hello() {
        logger.info("Hello Resource Cstor");
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> hello() {
        Map<String, Object> m = new HashMap<>();
//        return "Bonjour world!!! OoQ";
        m.put("Path", uriInfo.getPath());
        m.put("Absolute Path", uriInfo.getAbsolutePath());
        m.put("Base Uri", uriInfo.getBaseUri());
        m.put("Matched Uris", String.join(",", uriInfo.getMatchedURIs()));
        m.put("Request Uri", uriInfo.getRequestUri());
        return m;
    }

    @Path("{greeting: .+}")
    public CustomHelloResource getCustom(@PathParam("greeting")String greeting) {
        return new CustomHelloResource(greeting, uriInfo);
    }

    @Path("request")
    public RequestResource request() {
        return new RequestResource(request);
    }
}
