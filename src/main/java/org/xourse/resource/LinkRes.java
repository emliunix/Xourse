package org.xourse.resource;

import org.xourse.entity.Admin;
import org.xourse.entity.Student;
import org.xourse.entity.Teacher;
import org.xourse.entity.User;
import org.xourse.utils.SessionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * Links of a user
 * Created by Liu Yuhui on 2015/12/3.
 */
@Path("/link")
public class LinkRes {
    @Context
    HttpServletRequest request;

    private static final Link[] STUDENT_LINKS = new Link[] {
            new Link("#/welcome", "首页"),
            new Link("#/readnews", "查看新闻"),
            new Link("#/chooseSubject", "课程选择"),
            new Link("#/stuselectYear", "查询课程信息"),
            new Link("#/ext/queryHubuScore", "查询湖大成绩")
    };
    private static final Link[] TEACHER_LINKS = new Link[] {
            new Link("#/welcome", "首页"),
            new Link("#/readnews", "查看新闻"),
            new Link("#/teachSubject", "公选课程选择"),
            new Link("#/selectYear", "成绩结算")
    };
    private static final Link[] ADMIN_LINKS = new Link[] {
            new Link("#/newsmanage", "新闻管理"),
//            new Link("#/usermanage", "用户管理"),
            new Link("#/departmentmanage", "院系管理"),
            new Link("#/majormanage", "专业管理"),
            new Link("#/classmanage", "行政班级管理"),
            new Link("#/stumanage", "学生管理"),
            new Link("#/teamanage", "教师管理"),
            new Link("#/compulsorymanage", "必修课管理"),
            new Link("#/courseplanmanage", "课程计划管理")
    };

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public LinkInfo get() {
        User u = SessionUtils.getUser(request);
        if(null == u) return new LinkInfo("user not logged in");

        if(u instanceof Student || "student".equals(u.getRole())) {
            return new LinkInfo(STUDENT_LINKS);
        } else if(u instanceof Teacher || "teacher".equals(u.getRole())) {
            return new LinkInfo(TEACHER_LINKS);
        } else if(u instanceof Admin || "admin".equals(u.getRole())) {
            return new LinkInfo(ADMIN_LINKS);
        } else {
            return new LinkInfo("invalid user");
        }
    }

    public static class LinkInfo {
        public String msg = "success";
        public boolean status = true;
        public Link[] links;

        /**
         * Success message
         * @param links
         */
        public LinkInfo(Link[] links) {
            this.links = links;
        }

        /**
         * Error message
         * @param msg
         */
        public LinkInfo(String msg) {
            this.msg = msg;
            status = false;
        }
    }

    public static class Link {
        public String text;
        public String href;

        public Link(String href, String text) {
            this.text = text;
            this.href = href;
        }
    }
}
