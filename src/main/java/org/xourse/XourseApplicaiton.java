package org.xourse;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.apache.log4j.Logger;
import org.glassfish.jersey.CommonProperties;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.spring.SpringComponentProvider;
import org.xourse.utils.MessageUtils;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Api 入口
 * 配置jackson json特性
 * Created by Liu Yuhui on 2015/11/26.
 */
public class XourseApplicaiton extends ResourceConfig {

    public XourseApplicaiton() {
        packages("org.xourse.resource");
        register(JacksonFeature.class);
        register(SpringComponentProvider.class);
        register(ServiceExceptionMapper.class);
        property(CommonProperties.METAINF_SERVICES_LOOKUP_DISABLE, true);
        property(CommonProperties.FEATURE_AUTO_DISCOVERY_DISABLE, true);
    }

    public static class ServiceExceptionMapper implements ExceptionMapper<Exception> {
        @Override
        public Response toResponse(Exception exception) {
            return Response.status(500).entity(MessageUtils.fail(exception.getMessage())).build();
        }
    }
}
