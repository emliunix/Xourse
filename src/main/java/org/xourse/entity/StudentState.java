package org.xourse.entity;

import javax.persistence.*;

/**
 * Save a state whether a student has done his selection of elective courses
 * Created by Liu Yuhui on 2015/12/3.
 */
@Entity
public class StudentState {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private String year;
    @Column
    private Boolean modifiable = true;
    @ManyToOne(optional = false)
    private Student student;

    public StudentState() { }

    public StudentState(Student s, String year) {
        this.student = s;
        this.year = year;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
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
