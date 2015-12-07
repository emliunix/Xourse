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
    @Column
    private String year;
    @Column
    private String teacherId;
    @Column
    private String name;
    @JsonIgnore
    @ManyToOne
    private Department department;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private TeacherProfile teacherProfile;

    public Teacher() { }

    public Teacher(int id) {
        setId(id);
    }

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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
