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
    private List<CourseRegistration> registrations = new ArrayList<>(0);

    public final String type = "必修课程";

    public CompulsoryCourse() { }

    public CompulsoryCourse(int id) {
        setId(id);
    }

    public CompulsoryCourse(String name, String year) {
        setName(name);
        setYear(year);
    }

    public MajorClass getMajorClass() {
        return majorClass;
    }

    public void setMajorClass(MajorClass majorClass) {
        this.majorClass = majorClass;
    }

    public List<CourseRegistration> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(List<CourseRegistration> registrations) {
        this.registrations = registrations;
    }
}
