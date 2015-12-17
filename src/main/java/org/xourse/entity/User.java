package org.xourse.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Liu Yuhui on 2015/11/6.
 */
@Entity(name = "User")
@NamedQueries({
        @NamedQuery(name = "findAllUsers", query = "FROM User"),
        @NamedQuery(name = "findAllTeachers", query = "from Teacher"),
        @NamedQuery(name = "findAllStudents", query = "from Student"),
        @NamedQuery(name = "findAllAdmins", query = "from Admin"),
        @NamedQuery(name = "findUserByUsername" ,query = "from User u where u.username = :username")
})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("user")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    protected String role;
    {
        role = "user";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return role + "{" + id + ", " + username + '}';
    }
}
