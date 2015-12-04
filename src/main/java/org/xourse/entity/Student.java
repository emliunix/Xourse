package org.xourse.entity;

import javax.persistence.*;

/**
 * Created by Liu Yuhui on 2015/11/24.
 */
@Entity
@DiscriminatorValue("student")
public class Student extends User {
    {
        role = "student";
    }
    @Column
    private String year;
    @Column
    private String studentId;
    @ManyToOne
    private MajorClass majorClass;
    @OneToOne(cascade = CascadeType.ALL)
    private StudentProfile studentProfile;

    public Student() { }
    public Student(int id) {
        setId(id);
    }

    public MajorClass getMajorClass() {
        return majorClass;
    }

    public void setMajorClass(MajorClass majorClass) {
        this.majorClass = majorClass;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public StudentProfile getStudentProfile() {
        return studentProfile;
    }

    public void setStudentProfile(StudentProfile studentProfile) {
        this.studentProfile = studentProfile;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
