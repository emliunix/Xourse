package org.xourse.resource.info;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.xourse.entity.Department;
import org.xourse.entity.Teacher;
import org.xourse.entity.TeacherProfile;

/**
 * Created by Liu Yuhui on 2015/12/2.
 */
public class TeacherInfo {
    public Integer id;
    public String name;
    public String number;
    public String teacherId;
    public String year;
    public TeacherInfo(Teacher t) { id = t.getId(); number = t.getUsername(); name = t.getName(); teacherId = t.getTeacherId(); year = t.getYear(); }

    public static class WithDepartment extends TeacherInfo {
        public Department department;
        public WithDepartment(Teacher t) { super(t); department = t.getDepartment(); }
    }

    public static class Detailed extends WithDepartment {
        @JsonIgnoreProperties("id")
        @JsonUnwrapped
        public TeacherProfile profile;
        public Detailed(Teacher t) { super(t); profile = t.getTeacherProfile(); }
    }
}
