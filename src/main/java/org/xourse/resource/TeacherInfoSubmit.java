package org.xourse.resource;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.xourse.entity.Teacher;
import org.xourse.entity.TeacherProfile;

/**
 * Created by Liu Yuhui on 2015/11/29.
 */
public class TeacherInfoSubmit {
    public Integer departmentId;
    @JsonUnwrapped
    public Teacher teacher;
    @JsonUnwrapped
    public TeacherProfile profile;
}
