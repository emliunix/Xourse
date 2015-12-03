package org.xourse.resource;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xourse.entity.Major;
import org.xourse.entity.MajorClass;
import org.xourse.resource.info.MajorClassInfo;
import org.xourse.resource.info.StudentInfo;
import org.xourse.service.MajorClassService;
import org.xourse.utils.MessageUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Major class service
 * Created by Liu Yuhui on 2015/11/30.
 */
@Component
@Path("/major_class")
public class MajorClassesRes {

    @Autowired
    private MajorClassService majorClassService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getMajorClasses() {
        List<MajorClassInfo> classes = null;
        try {
            classes = majorClassService.findAll().stream()
            .map(MajorClassInfo::new).collect(Collectors.toList());
        } catch (Exception e) {
            return MessageUtils.fail("failed");
        }
        Map<String, Object> m = MessageUtils.success("success");
        m.put("classes", classes);
        return m;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> createMajorClass(MajorClassSubmit submit) {
        MajorClass clazz = submit.majorClass;
        if(null != submit.majorId) {
            Major m = new Major();
            m.setId(submit.majorId);
            clazz.setMajor(m);
        }
        try {
            majorClassService.create(clazz);
        } catch (Exception e) {
            return MessageUtils.fail(e.getMessage());
        }
        Map<String, Object> m = MessageUtils.success("create MajorClass success");
        m.put("MajorClass", clazz);
        return m;
    }

    @Path("/{id}")
    public MajorClassRes doPath(@PathParam("id")String id) {
        int _id = Integer.valueOf(id);
        return new MajorClassRes(_id);
    }

    public static class MajorClassSubmit {
        public Integer majorId;
        @JsonUnwrapped
        public MajorClass majorClass;
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
        public Map<String, Object> updateMajorClass(MajorClassSubmit submit) {
            if(null == submit)
                return MessageUtils.fail("invalid data");
            MajorClass clazz = submit.majorClass;
            clazz.setId(id);
            if(null != submit.majorId) {
                Major m = new Major();
                m.setId(submit.majorId);
                clazz.setMajor(m);
            }
            try {
                clazz = majorClassService.update(clazz);
            } catch (Exception e) {
                return MessageUtils.fail("update MajorClass failed");
            }
            Map<String, Object> m = MessageUtils.success("update MajorClass success");
            m.put("majorClass", clazz);
            return m;
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
            List<StudentInfo> stus;
            try {
                stus = majorClassService.findStudents(id).stream()
                .map(StudentInfo::new).collect(Collectors.toList());
            } catch (Exception e) {
                return MessageUtils.fail(e.getMessage());
            }
            Map<String, Object> m = MessageUtils.success();
            m.put("students", stus);
            return m;
        }
    }
}
