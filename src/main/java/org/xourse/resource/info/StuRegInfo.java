package org.xourse.resource.info;

import org.xourse.entity.CourseRegistration;

/**
 * Created by Liu Yuhui on 2015/12/18.
 */
public class StuRegInfo {
    public Integer id;
    public String name;
    public String regularGrade;
    public String finalExamGrade;
    public String finalGrade;

    public StuRegInfo(CourseRegistration reg) {
        id = reg.getStudent().getId();
        name = reg.getStudent().getName();
        regularGrade = reg.getRegularGrade();
        finalExamGrade = reg.getFinalExamGrade();
        finalGrade = reg.getFinalGrade();
    }

    public StuRegInfo() {

    }
}
