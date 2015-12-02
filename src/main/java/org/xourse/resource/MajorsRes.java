package org.xourse.resource;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xourse.entity.Department;
import org.xourse.entity.Major;
import org.xourse.entity.MajorClass;
import org.xourse.resource.info.MajorClassInfo;
import org.xourse.resource.info.MajorInfo;
import org.xourse.service.MajorService;
import org.xourse.utils.MessageUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Major service
 * Created by Liu Yuhui on 2015/11/30.
 */
@Component
@Path("/majors")
public class MajorsRes {

    @Autowired
    private MajorService majorService;

    /**
     * Get all majors' info
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getMajors() {
        List<MajorInfo> ms = null;
        try {
            ms = majorService.findAll().stream()
            .map(MajorInfo::new).collect(Collectors.toList());
        } catch (Exception e) {
            return MessageUtils.fail("failed");
        }
        Map<String, Object> m = MessageUtils.success("success");
        m.put("majors", ms);
        return m;
    }

    /**
     * Create a new major
     * @param submit
     * @return
     */
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
            return MessageUtils.fail(e.getMessage());
        }
        Map<String, Object> m = MessageUtils.success("create Major success");
        m.put("major", submit.major);
        return m;
    }

    /**
     * Delegate ops to a specific major
     * @param id
     * @return
     */
    @Path("/{id}")
    public MajorRes doPath(@PathParam("id")String id) {
        int _id = Integer.valueOf(id);
        return new MajorRes(_id);
    }

    /**
     * Package of info related to a submit creation or update info
     */
    public static class MajorSubmit {
        public Integer departmentId;
        @JsonUnwrapped
        public Major major;
    }

    /**
     * Operations on a specific major
     */
    public class MajorRes {
        // major's id
        private final int id;

        public MajorRes(int id) {
            this.id = id;
        }

        /**
         * Major's detailed info
         * @return
         */
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

        /**
         * Update a major
         * @param submit
         * @return
         */
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

        /**
         * delete a major
         * @return
         */
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

        /**
         * Get a list of classes of this major
         * @return
         */
        @Path("/classes")
        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> getStudents() {
            List<MajorClassInfo> classes;
            try {
                classes = majorService.findMajorClasses(id).stream()
                .map(MajorClassInfo::new).collect(Collectors.toList());
            } catch (Exception e) {
                return MessageUtils.fail(e.getMessage());
            }
            Map<String, Object> m = MessageUtils.success();
            m.put("classes", classes);
            return m;
        }
    }
}
