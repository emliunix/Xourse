package org.xourse.entity;

import javax.persistence.*;

/**
 * Created by Liu Yuhui on 2015/11/28.
 */
@Entity
public class ElectiveCourseRegistration {
    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne
    private ElectiveClass electiveClass;
    @OneToOne
    private Student student;
}
