package org.xourse.resource;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xourse.entity.Department;
import org.xourse.entity.Major;
import org.xourse.entity.MajorClass;
import org.xourse.service.MajorService;
import org.xourse.utils.MessageUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

/**
 * Major service
 * Created by Liu Yuhui on 2015/11/30.
 */
@Component
@Path("/majors")
public class MajorsRes {

    @Autowired
    private MajorService majorService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getMajors() {
        List<Major> ds = null;
        try {
            ds = majorService.findAll();
        } catch (Exception e) {
            return MessageUtils.fail("failed");
        }
        Map<String, Object> m = MessageUtils.success("success");
        m.put("majors", ds);
        return m;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> createMajor(MajorSubmit submit) {
        try {
            Department d = null;
            if(null != submit.departmentId) {
                d = new Department();
                d.setId(submit.departmentId);
                submit.major.setDepartment(d);
            }
            majorService.create(submit.major);
        } catch (Exception e) {
            return MessageUtils.fail("create Major failed");
        }
        Map<String, Object> m = MessageUtils.success("create Major success");
        m.put("major", submit.major);
        return m;
    }

    @Path("/{id}")
    public MajorRes doPath(@PathParam("id")String id) {
        int _id = Integer.valueOf(id);
        return new MajorRes(_id);
    }

    public static class MajorSubmit {
        public Integer departmentId;
        @JsonUnwrapped
        public Major major;
    }

    public class MajorRes {

        private final int id;

        public MajorRes(int id) {
            this.id = id;
        }

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> getMajor() {
            Major d = majorService.findById(id);
            if(null == d) {
                return MessageUtils.fail("cannot find Major[" + id + "]");
            } else {
                Map<String, Object> m = MessageUtils.success("found Major [" + id + "]");
                m.put("major", d);
                return m;
            }
        }

        @PUT
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> updateMajor(MajorSubmit submit) {
            if(null == submit)
                return MessageUtils.fail("invalid data");
            submit.major.setId(id);
            if(null != submit.departmentId) {
                Department d = new Department();
                d.setId(submit.departmentId);
                submit.major.setDepartment(d);
            }
            try {
                majorService.update(submit.major);
            } catch (Exception e) {
                return MessageUtils.fail(e.getMessage());
            }
            return MessageUtils.success();
        }

        @DELETE
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> deleteMajor() {
            try {
                majorService.delete(id);
            } catch (Exception e) {
                return MessageUtils.fail(e.getMessage());
            }
            return MessageUtils.success();
        }

        @Path("/classes")
        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> getStudents() {
            List<MajorClass> classes;
            try {
                classes = majorService.findMajorClasses(id);
            } catch (Exception e) {
                return MessageUtils.fail(e.getMessage());
            }
            Map<String, Object> m = MessageUtils.success();
            m.put("classes", classes);
            return m;
        }
    }
}
