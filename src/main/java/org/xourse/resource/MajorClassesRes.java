package org.xourse.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xourse.entity.MajorClass;
import org.xourse.entity.Student;
import org.xourse.service.MajorClassService;
import org.xourse.utils.MessageUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

/**
 * Major class service
 * Created by Liu Yuhui on 2015/11/30.
 */
@Component
@Path("/major_classes")
public class MajorClassesRes {

    @Autowired
    private MajorClassService majorClassService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getMajorClasses() {
        List<MajorClass> classes = null;
        try {
            classes = majorClassService.findAll();
        } catch (Exception e) {
            return MessageUtils.fail("failed");
        }
        Map<String, Object> m = MessageUtils.success("success");
        m.put("classes", classes);
        return m;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> createMajorClass(MajorClass d) {
        try {
            majorClassService.create(d);
        } catch (Exception e) {
            return MessageUtils.fail("create MajorClass failed");
        }
        Map<String, Object> m = MessageUtils.success("create MajorClass success");
        m.put("MajorClass", d);
        return m;
    }

    @Path("/{id}")
    public MajorClassRes doPath(@PathParam("id")String id) {
        int _id = Integer.valueOf(id);
        return new MajorClassRes(_id);
    }

    public class MajorClassRes {

        private int id;

        public MajorClassRes(int id) {
            this.id = id;
        }

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> getMajorClass() {
            MajorClass d = majorClassService.findById(id);
            if(null == d) {
                return MessageUtils.fail("cannot find MajorClass[" + id + "]");
            } else {
                Map<String, Object> m = MessageUtils.success("found MajorClass [" + id + "]");
                m.put("MajorClass", d);
                return m;
            }
        }

        @PUT
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> updateMajorClass(MajorClass d) {
            if(null == d)
                return MessageUtils.fail("invalid data");
            d.setId(id);
            try {
                majorClassService.update(d);
            } catch (Exception e) {
                return MessageUtils.fail("update MajorClass failed");
            }
            return MessageUtils.success("update MajorClass success");
        }

        @DELETE
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> deleteMajorClass() {
            try {
                majorClassService.delete(id);
            } catch (Exception e) {
                return MessageUtils.fail("delete MajorClass failed");
            }
            return MessageUtils.success("delete MajorClass success");
        }

        @Path("/students")
        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> getStudents() {
            List<Student> stus;
            try {
                stus = majorClassService.findStudents(id);
            } catch (Exception e) {
                return MessageUtils.fail(e.getMessage());
            }
            Map<String, Object> m = MessageUtils.success();
            m.put("students", stus);
            return m;
        }
    }
}
