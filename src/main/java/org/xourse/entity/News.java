package org.xourse.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.ValueGenerationType;
import org.hibernate.tuple.CreationTimestampGeneration;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Liu Yuhui on 2015/12/7.
 */
@Entity
public class News {
    @Id
    @GeneratedValue
    public Integer id;
    @Column
    public String title;
    @Column(length = 10000)
    public String content;
    @Column
    @CreationTimestamp
    public Date ctime;

    public News() {

    }

    public News(int id) {
        this.id = id;
    }
}
