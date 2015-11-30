package org.xourse.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by Liu Yuhui on 2015/11/24.
 */
@Entity
@DiscriminatorValue("teacher")
public class Teacher extends User {
    {
        role = "teacher";
    }

    @JsonIgnore
    @ManyToOne
    private Department department;
    @Column
    private String teacherId;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private TeacherProfile teacherProfile;

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public TeacherProfile getTeacherProfile() {
        return teacherProfile;
    }

    public void setTeacherProfile(TeacherProfile teacherProfile) {
        this.teacherProfile = teacherProfile;
    }
}
