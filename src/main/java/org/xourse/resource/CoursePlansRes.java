package org.xourse.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.xourse.entity.CoursePlan;
import org.xourse.service.CoursePlanService;
import org.xourse.utils.MessageUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

/**
 * Course Plan
 * Created by Liu Yuhui on 2015/12/2.
 */
@Path("/course_plan")
public class CoursePlansRes {

    @Autowired
    CoursePlanService coursePlanService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> get() {
        List<CoursePlan> ps;
        try {
            ps = coursePlanService.findAll();
        } catch (Exception e) {
            return MessageUtils.fail(e.getMessage());
        }

        Map<String, Object> m = MessageUtils.success();
        m.put("plans", ps);
        return m;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> create(CoursePlan p) {
        try {
            coursePlanService.create(p);
        } catch (Exception e) {
            return MessageUtils.fail(e.getMessage());
        }
        Map<String, Object> m = MessageUtils.success();
        m.put("plan", p);
        return m;
    }

    @Path("{id : \\d+}")
    public CoursePlanRes doPath(@PathParam("id")String id) {
        return new CoursePlanRes(Integer.valueOf(id));
    }

    public class CoursePlanRes {
        private int id;

        public CoursePlanRes(int id) {
            this.id = id;
        }

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> get() {
            CoursePlan p;
            try {
                p = coursePlanService.findById(id);
            } catch (Exception e) {
                return MessageUtils.fail(e.getMessage());
            }
            Map<String, Object> m = MessageUtils.success();
            m.put("plan", p);
            return m;
        }

        @PUT
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> update(CoursePlan p) {
            if(null == p)
                return MessageUtils.fail("invalid submit");
            p.setId(id);
            try {
                p = coursePlanService.update(p);
            } catch (Exception e) {
                return MessageUtils.fail(e.getMessage());
            }
            Map<String, Object> m = MessageUtils.success();
            m.put("plan", p);
            return m;
        }

        @DELETE
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> delete() {
            try {
                coursePlanService.delete(id);
            } catch (Exception e) {
                return MessageUtils.fail(e.getMessage());
            }
            return MessageUtils.success();
        }
    }
}
