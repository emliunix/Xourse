package org.xourse.entity;

import javax.persistence.*;

/**
 * Created by Liu Yuhui on 2015/11/28.
 */
@Entity
public class CompulsoryCourseRegistration {
    @Id
    @GeneratedValue
    private Integer id;
    @Column
    private Double regularGrade;
    @Column
    private Double finalExamGrade;
    @ManyToOne
    private CompulsoryCourse course;
    @ManyToOne
    private Student student;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getRegularGrade() {
        return regularGrade;
    }

    public void setRegularGrade(Double regularGrade) {
        this.regularGrade = regularGrade;
    }

    public Double getFinalExamGrade() {
        return finalExamGrade;
    }

    public void setFinalExamGrade(Double finalExamGrade) {
        this.finalExamGrade = finalExamGrade;
    }

    public CompulsoryCourse getCourse() {
        return course;
    }

    public void setCourse(CompulsoryCourse course) {
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
