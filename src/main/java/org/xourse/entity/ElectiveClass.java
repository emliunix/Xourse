package org.xourse.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liu Yuhui on 2015/11/28.
 */
@Entity
public class ElectiveClass {
    @Id
    @GeneratedValue
    private Integer id;
    @Column
    private String name;
    @OneToMany
    private List<ElectiveCourseRegistration> registrations = new ArrayList<>(0);
}
