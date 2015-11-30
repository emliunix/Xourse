package org.xourse.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Liu Yuhui on 2015/11/28.
 */
@Entity
public class CompulsoryCourseRegistration {
    @Id
    @GeneratedValue
    private Integer id;
}
