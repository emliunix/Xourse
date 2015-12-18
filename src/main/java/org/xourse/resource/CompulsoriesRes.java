package org.xourse.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.xourse.entity.CompulsoryCourse;
import org.xourse.entity.CoursePlan;
import org.xourse.entity.MajorClass;
import org.xourse.entity.Teacher;
import org.xourse.service.CourseService;
import org.xourse.utils.MessageUtils;
import org.xourse.utils.TaskRunner;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Resource Template
 * Created by Liu Yuhui on 2015/11/26.
 */
@Component
@Path("/compulsory")
public class CompulsoriesRes {

    @Autowired
    CourseService courseService;

    private TaskRunner<Map<String, Object>> taskRunner = new TaskRunner<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> get(@RequestParam("year")String year) {
        return taskRunner.run(() -> {
            Map<String, Object> m = MessageUtils.success();
            List<CompulsoryCourse> ccs = null;
            if (null != year && year.matches("\\d{4}-\\d{4}-[12]")) {
                ccs = courseService.findCompulsoryByYear(year);
            } else {
                ccs = courseService.findAllCompulsory();
            }
            m.put("courses", ccs);
            return m;
        }, MessageUtils::fail);
    }

    @Path("/plans")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> get() {
        return taskRunner.run(() -> {
            Map<String, Object> m = MessageUtils.success();
            List<CoursePlan> ccs = null;

            ccs = courseService.findAllCompulsoryPlan();
            m.put("plans", ccs);
            return m;
        }, MessageUtils::fail);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> create(CompulsorySubmit submit) {
        return taskRunner.run(() -> {
            Map<String, Object> m = MessageUtils.success();
            if(null == submit.classId || null == submit.teacherId ||
                    null == submit.name || "".equals(submit.name) ||
                    null == submit.year || "".equals(submit.year)) {
                throw new IllegalArgumentException("Compulsory submit invalid");
            }

            MajorClass c = new MajorClass(submit.classId);
            Teacher t = new Teacher(submit.teacherId);

            // get year
/*
            String year = submit.year;
            if(null == year)
                year = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
*/

            CompulsoryCourse course = new CompulsoryCourse(submit.name, submit.year);
            course.setMajorClass(c);
            course.setTeacher(t);

            courseService.createCompulsory(course);

            return m;
        }, MessageUtils::fail);
    }

    @Path("{id : \\d+}")
    public CompulsoryRes doPath(@PathParam("id")String id) {
        return new CompulsoryRes(Integer.valueOf(id));
    }

    public static class CompulsorySubmit {
        public String name;
        public Integer teacherId;
        public Integer classId;
        public String year;
    }

    public class CompulsoryRes {
        private int id;

        public CompulsoryRes(int id) {
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
