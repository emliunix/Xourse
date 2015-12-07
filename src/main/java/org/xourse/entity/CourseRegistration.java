package org.xourse.entity;

import javax.persistence.*;

/**
 * Created by Liu Yuhui on 2015/11/28.
 */
@Entity
public class CourseRegistration {
    @Id
    @GeneratedValue
    private Integer id;
    @Column
    private String regularGrade = "-";
    @Column
    private String finalExamGrade = "-";
    @ManyToOne
    private Course course;
    @ManyToOne
    private Student student;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRegularGrade() {
        return regularGrade;
    }

    public void setRegularGrade(String regularGrade) {
        this.regularGrade = regularGrade;
    }

    public String getFinalExamGrade() {
        return finalExamGrade;
    }

    public void setFinalExamGrade(String finalExamGrade) {
        this.finalExamGrade = finalExamGrade;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
