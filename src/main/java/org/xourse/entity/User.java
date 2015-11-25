package org.xourse.entity;

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
        @NamedQuery(name = "findUserByUsername" ,query = "from User u where u.username = :username")
})
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("user")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User {
    @Id
    private String id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    protected String role;
    {
        role = "user";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
        return role + "{" +
                id +
                ", " + username +
                '}';
    }
}
