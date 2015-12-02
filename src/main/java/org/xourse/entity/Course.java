package org.xourse.entity;

import javax.persistence.*;

/**
 * Created by Liu Yuhui on 2015/12/2.
 */
@Entity
public class Course {
    @Id
    @GeneratedValue
    private Integer id;
    @Column
    private String year;
    @Column
    private String name;
    @Column
    private int regularRate = 60;
    @Column
    private int finalExamRate = 40;
    @ManyToOne
    private Teacher teacher;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public int getRegularRate() {
        return regularRate;
    }

    public void setRegularRate(int regularRate) {
        this.regularRate = regularRate;
    }

    public int getFinalExamRate() {
        return finalExamRate;
    }

    public void setFinalExamRate(int finalExamRate) {
        this.finalExamRate = finalExamRate;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
