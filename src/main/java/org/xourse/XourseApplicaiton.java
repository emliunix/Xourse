package org.xourse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.apache.log4j.Logger;
import org.glassfish.jersey.CommonProperties;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.spring.SpringComponentProvider;
import org.xourse.utils.MessageUtils;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Api 入口
 * 配置jackson json特性
 * Created by Liu Yuhui on 2015/11/26.
 */
public class XourseApplicaiton extends ResourceConfig {

    public XourseApplicaiton() {
        packages(false, "org.xourse.resource");
        register(JacksonFeature.class);
        register(SpringComponentProvider.class);
        register(ServiceExceptionMapper.class);
        register(HibernateAwareObjectMapperProvider.class);
        property(CommonProperties.METAINF_SERVICES_LOOKUP_DISABLE, true);
        property(CommonProperties.FEATURE_AUTO_DISCOVERY_DISABLE, true);
    }

    @Provider
    public static class HibernateAwareObjectMapperProvider implements ContextResolver<ObjectMapper> {
        private final ObjectMapper objectMapper;
        {
            objectMapper = createObjectMapper();
        }

        private static ObjectMapper createObjectMapper() {
            final ObjectMapper om = new ObjectMapper();
            om.registerModule(new Hibernate4Module());
            return om;
        }

        @Override
        public ObjectMapper getContext(Class<?> type) {
            return objectMapper;
        }
    }

    public static class ServiceExceptionMapper implements ExceptionMapper<Exception> {
        @Override
        public Response toResponse(Exception exception) {
            return Response.status(500).entity(MessageUtils.fail(exception.getMessage())).build();
        }
    }
}
