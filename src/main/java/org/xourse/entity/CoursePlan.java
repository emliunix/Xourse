package org.xourse.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liu Yuhui on 2015/11/28.
 */
@Entity
public class CoursePlan {
    @Id
    @GeneratedValue
    private int id;
    @Column
    private String name;
    @Column
    private CourseType type;

    public CoursePlan() {

    }

    public CoursePlan(String name, CourseType type) {
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CourseType getType() {
        return type;
    }

    public void setType(CourseType type) {
        this.type = type;
    }

    public enum CourseType { COMPULSORY, ELECTIVE }

}
