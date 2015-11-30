package org.xourse.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Liu Yuhui on 2015/11/24.
 */
@Entity
public class Department {
    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "department")
    private List<Major> majors;

    @JsonIgnore
    @OneToMany(mappedBy = "department")
    private List<Teacher> teachers;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Major> getMajors() {
        return majors;
    }

    public void setMajors(List<Major> majors) {
        this.majors = majors;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }
}
