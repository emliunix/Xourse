package org.xourse.resource.info;

import org.xourse.entity.Student;

/**
 * Created by Liu Yuhui on 2015/12/2.
 */
public class StudentInfo {
    public Integer id;
    public String name;

    public StudentInfo(Student s) {
        id = s.getId();
        name = s.getUsername();
    }
}
