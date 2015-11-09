package org.liu.rs;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

import javax.ws.rs.ApplicationPath;

/**
 * Created by Liu Yuhui on 2015/10/9.
 */
@ApplicationPath("/resource")
public class TestApp extends ResourceConfig{
    public TestApp() {
        packages("org.liu.restapi");
        register(JacksonFeature.class);
        register(RequestContextFilter.class);
    }
}
