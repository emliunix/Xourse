package org.xourse.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.xourse.entity.User;
import org.xourse.service.AccessControlService;
import org.xourse.utils.MessageUtils;
import org.xourse.utils.SessionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;

/**
 * Created by Liu Yuhui on 2015/11/26.
 */
@Path("/teacher_self")
@Component
public class TeacherSelf {
    @Autowired
    TeacherRes teacherRes;

    @Autowired
    AccessControlService aclService;

    @Autowired
    HttpServletRequest request;

    @Path("/")
    public TeacherRes delegate() {
        User user = SessionUtils.getUser(request);
        if(null == user || !aclService.canAccess(user, AccessControlService.ROLE_TEACHER))
            return null;

        teacherRes.setId(user.getId());
        return teacherRes;
    }
}
