package org.xourse.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liu Yuhui on 2015/11/24.
 */
@Entity
public class Major {
    @Id
    private long id;

    @Column
    private String name;

    @ManyToOne
    private Department department;

    @OneToMany(mappedBy = "major")
    private List<MajorClass> classes = new ArrayList<>(0);

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
