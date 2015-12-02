package org.xourse.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Map;

/**
 * Resource Template
 * Created by Liu Yuhui on 2015/11/26.
 */
//@Component
//@Path("/templates")
public class TemplatesRes {

    //@Autowired
    //TemplateService templateService

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> get() {
        return null;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> create() {
        return null;
    }

    @Path("{id : \\d+}")
    public TemplateRes doPath(@PathParam("id")String id) {
        return new TemplateRes(Integer.valueOf(id));
    }

    public class TemplateRes {
        private int id;

        public TemplateRes(int id) {
            this.id = id;
        }

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> get() {
            return null;
        }

        @PUT
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> update() {
            return null;
        }

        @DELETE
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> delete() {
            return null;
        }
    }
}
