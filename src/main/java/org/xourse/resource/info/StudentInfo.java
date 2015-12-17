package org.xourse.resource.info;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.xourse.entity.Student;
import org.xourse.entity.StudentProfile;

/**
 * Created by Liu Yuhui on 2015/12/2.
 */
public class StudentInfo {
    public Integer id;
    public String number;
    public String name;
    public String year;
    public String studentId;

    public StudentInfo(Student s) {
        id = s.getId();
        name = s.getName();
        number = s.getUsername();
        studentId = s.getStudentId();
        year = s.getYear();
    }
    public static class WithClass extends StudentInfo {
        public MajorClassInfo.WithMajor classInfo;
        public WithClass(Student s) {
            super(s);
            if(null != s.getMajorClass())
                classInfo = new MajorClassInfo.WithMajor(s.getMajorClass());
        }
    }

    public static class Detailed extends WithClass {
        @JsonIgnoreProperties("id")
        @JsonUnwrapped
        public StudentProfile profile;
        public Detailed(Student s) {
            super(s);
            profile = s.getStudentProfile();
        }
    }
}
