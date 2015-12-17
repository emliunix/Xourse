package org.xourse.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liu Yuhui on 2015/11/28.
 */
@Entity
public class ElectiveCourse extends Course {
    @JsonIgnore
    @OneToMany(mappedBy = "course")
    private List<CourseRegistration> registrations = new ArrayList<>(0);

    public final String type = "选修课程";

    public ElectiveCourse() { }

    public ElectiveCourse(int id) {
        setId(id);
    }

    public ElectiveCourse(String name, String year) {
        setName(name);
        setYear(year);
    }

    public List<CourseRegistration> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(List<CourseRegistration> registrations) {
        this.registrations = registrations;
    }
}
