package org.xourse.resource;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xourse.entity.Department;
import org.xourse.entity.Major;
import org.xourse.entity.Teacher;
import org.xourse.resource.info.MajorInfo;
import org.xourse.resource.info.TeacherInfo;
import org.xourse.service.DepartmentService;
import org.xourse.utils.MessageUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Departments Resource
 * Created by Liu Yuhui on 2015/11/30.
 */
@Path("/department")
@Component
public class DepartmentsRes {

    @Autowired
    private DepartmentService departmentService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getDepartments() {
        List<Department> ds = null;
        try {
            ds = departmentService.findAll();
        } catch (Exception e) {
            return MessageUtils.fail("failed");
        }
        Map<String, Object> m = MessageUtils.success("success");
        m.put("departments", ds);
        return m;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> createDepartment(Department d) {
        try {
            departmentService.create(d);
        } catch (Exception e) {
            return MessageUtils.fail("create department failed");
        }
        Map<String, Object> m = MessageUtils.success("create department success");
        m.put("department", d);
        return m;
    }

    @Path("/{id}")
    public DepartmentRes doPath(@PathParam("id")String id) {
        int _id = Integer.valueOf(id);
        return new DepartmentRes(_id);
    }

    public class DepartmentRes {

        private int id;

        public DepartmentRes(int id) {
            this.id = id;
        }

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> getDepartment() {
            Department d = departmentService.findById(id);
            if(null == d) {
                return MessageUtils.fail("cannot find department[" + id + "]");
            } else {
                Map<String, Object> m = MessageUtils.success("found department [" + id + "]");
                m.put("department", d);
                return m;
            }
        }

        @PUT
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> updateDepartment(Department d) {
            if(null == d)
                return MessageUtils.fail("invalid data");
            d.setId(id);
            try {
                departmentService.update(d);
            } catch (Exception e) {
                return MessageUtils.fail("update department failed");
            }
            return MessageUtils.success("update department success");
        }

        @DELETE
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> deleteDepartment() {
            try {
                departmentService.delete(id);
            } catch (Exception e) {
                return MessageUtils.fail("delete department failed");
            }
            return MessageUtils.success("delete department success");
        }

        @Path("/majors")
        @GET
        public Map<String, Object> getMajors() {
            List<MajorInfo> majors;
            try {
                majors = departmentService.getMajors(id).stream()
                .map(MajorInfo::new).collect(Collectors.toList());
            } catch (Exception e) {
                return MessageUtils.fail("failed");
            }

            Map<String, Object> m = MessageUtils.success("success");
            m.put("majors", majors);
            return m;
        }

        @Path("/teachers")
        @GET
        public Map<String, Object> getTeachers() {
            List<TeacherInfo> teachers;
            try {
                teachers = departmentService.getTeachers(id).stream()
                .map(TeacherInfo::new).collect(Collectors.toList());
            } catch (Exception e) {
                return MessageUtils.fail(e.getMessage());
            }
            Map<String, Object> m = MessageUtils.success("success");
            m.put("teachers", teachers);
            return m;
        }
    }
}

