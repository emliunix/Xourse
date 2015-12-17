package org.xourse.entity;

import javax.persistence.*;

/**
 * Save a state whether a teacher has done his selection of elective courses
 * Created by Liu Yuhui on 2015/12/3.
 */
@Entity
public class TeacherState {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private String year;
    @Column
    private Boolean modifiable = true;
    @ManyToOne(optional = false)
    private Teacher teacher;

    public TeacherState() { }

    public TeacherState(Teacher t, String year) {
        this.teacher = t;
        this.year = year;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Boolean getModifiable() {
        return modifiable;
    }

    public void setModifiable(Boolean modifiable) {
        this.modifiable = modifiable;
    }
}
