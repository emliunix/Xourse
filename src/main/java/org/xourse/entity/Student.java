package org.xourse.entity;

import javax.persistence.*;

/**
 * Created by Liu Yuhui on 2015/11/24.
 */
@Entity
@DiscriminatorValue("student")
public class Student extends User {
    {
        role = "student";
    }
    @ManyToOne
    private MajorClass majorClass;

    public MajorClass getMajorClass() {
        return majorClass;
    }

    public void setMajorClass(MajorClass majorClass) {
        this.majorClass = majorClass;
    }
}
