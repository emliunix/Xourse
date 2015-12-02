package org.xourse.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liu Yuhui on 2015/11/28.
 */
@Entity
public class CompulsoryCourse extends Course {
    @ManyToOne
    private MajorClass majorClass;
    @OneToMany(mappedBy = "course")
    private List<CompulsoryCourseRegistration> registrations = new ArrayList<>(0);

    public MajorClass getMajorClass() {
        return majorClass;
    }

    public void setMajorClass(MajorClass majorClass) {
        this.majorClass = majorClass;
    }

    public List<CompulsoryCourseRegistration> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(List<CompulsoryCourseRegistration> registrations) {
        this.registrations = registrations;
    }
}
