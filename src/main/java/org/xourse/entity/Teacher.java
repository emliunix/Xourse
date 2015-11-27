package org.xourse.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by Liu Yuhui on 2015/11/24.
 */
@Entity
@DiscriminatorValue("teacher")
public class Teacher extends User {
    {
        role = "teacher";
    }

    @JsonIgnore
    @ManyToOne
    private Department department;

}
