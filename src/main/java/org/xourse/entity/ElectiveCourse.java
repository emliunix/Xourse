package org.xourse.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Liu Yuhui on 2015/11/28.
 */
@Entity
public class ElectiveCourse {
    @Id
    @GeneratedValue
    private Integer id;
    @Column
    private String year;
    @Column
    private String name;
    @OneToOne
    private Teacher teacher;
    @OneToMany
    private List<ElectiveClass> electiveClasses;

}
