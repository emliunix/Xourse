package org.xourse.entity;

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
    private long id;

    private String name;

    @ManyToOne
    private Major major;

    @OneToMany(mappedBy = "majorClass")
    private List<Student> students = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
