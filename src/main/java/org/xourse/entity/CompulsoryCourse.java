package org.xourse.entity;

import javax.persistence.*;

/**
 * Created by Liu Yuhui on 2015/11/28.
 */
@Entity
public class CompulsoryCourse {
    @Id
    @GeneratedValue
    private Integer id;
    @Column
    private String year;
    @Column
    private PlanedCourse.CourseType type;
    @Column
    private String name;
    @OneToOne
    private Teacher teacher;
    @OneToOne
    private MajorClass majorClass;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public PlanedCourse.CourseType getType() {
        return type;
    }

    public void setType(PlanedCourse.CourseType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public MajorClass getMajorClass() {
        return majorClass;
    }

    public void setMajorClass(MajorClass majorClass) {
        this.majorClass = majorClass;
    }
}
