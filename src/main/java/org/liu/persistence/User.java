package org.liu.persistence;

import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * Created by Liu Yuhui on 2015/11/6.
 */
@Entity
@Table(name = "t_user")
public class User {
    @Id
    @Type(type = "long")
    @Column(name = "id")
    @GeneratedValue
    private long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public User() { }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
