package org.xourse.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Liu Yuhui on 2015/11/24.
 */
@Entity
public class Major {
    @Id
    @GeneratedValue
    private Integer id;
    @Column
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;
    @JsonIgnore
    @OneToMany(mappedBy = "major")
    @Column
    private List<MajorClass> classes = new ArrayList<>(0);

    public Major() { }

    public Major(String name) {
        this.name = name;
    }

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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<MajorClass> getClasses() {
        return classes;
    }

    public void setClasses(List<MajorClass> classes) {
        this.classes = classes;
    }
}
