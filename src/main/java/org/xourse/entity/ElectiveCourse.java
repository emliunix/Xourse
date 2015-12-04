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
    private List<ElectiveCourseRegistration> registrations = new ArrayList<>(0);

    public List<ElectiveCourseRegistration> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(List<ElectiveCourseRegistration> registrations) {
        this.registrations = registrations;
    }
}
