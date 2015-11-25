package org.xourse.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * Created by Liu Yuhui on 2015/11/24.
 */
@Entity
@DiscriminatorValue("admin")
public class Admin extends User {
    {
        role = "admin";
    }
}
