package org.xourse.entity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

/**
 * Created by Liu Yuhui on 2015/11/24.
 */
@Entity
public class Teacher extends User {

    @ManyToOne
    private Department department;

}
