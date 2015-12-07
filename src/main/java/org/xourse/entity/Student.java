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
    @Column
    private String name;
    @ManyToOne
    private MajorClass majorClass;
    @OneToOne(cascade = CascadeType.ALL)
    private StudentProfile studentProfile;

    public Student() { }

    public Student(int id) {
        setId(id);
    }

    public Student(String studentId, String name, String year,
                   String gender,
                   String idCardNumber,
                   String telNumber,
                   String email,
                   String residence,
                   String politicalStatus,
                   String signature) {

        this.studentId = studentId;
        this.setUsername(studentId);
        this.name = name;
        this.year = year;
        StudentProfile profile = new StudentProfile(gender, idCardNumber, telNumber, email, residence, politicalStatus, signature);
        this.studentProfile = profile;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
