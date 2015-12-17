package org.xourse.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 一个专业里面的一个班级
 *
 * Created by Liu Yuhui on 2015/11/24.
 */
@Entity
public class MajorClass {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(length = 15)
    private String year;
    @Column(length = 50)
    private String name;
    @ManyToOne
    private Major major;
    @JsonIgnore
    @OneToMany(mappedBy = "majorClass", fetch = FetchType.LAZY)
    private List<Student> students = new ArrayList<>();

    public MajorClass() {
    }

    public MajorClass(String name, String year) {
        this.name = name;
        this.year = year;
    }

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

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
