package org.xourse.service;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.xourse.entity.Admin;
import org.xourse.entity.Student;
import org.xourse.entity.Teacher;
import org.xourse.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Liu Yuhui on 2015/11/6.
 */
@Transactional
@Component
public class UserService {

    @Resource
    SessionFactory sessionFactory;

    public User findUser(String id) {
        Session session = sessionFactory.getCurrentSession();
        Object o = session.get(User.class, id);
        return (User)o;
    }

    public List<User> findAllUsers() {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.getNamedQuery("findAllUsers");
        return (List<User>)q.list();
    }

    public User findUserByName(String username) {
        Session session = sessionFactory.getCurrentSession();
        Iterator iterator = session.getNamedQuery("findUserByUsername").setParameter("username", username).list().iterator();
        while(iterator.hasNext()) {
            return (User)iterator.next();
        }
        return null;
    }

    public Serializable saveUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        Serializable se = session.save(user);
        return se;
    }

    public void updateUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.update(user);
    }

    public void deleteUser(String id) {
        Session session = sessionFactory.getCurrentSession();
        User u = new User();
        u.setId(id);
        session.delete(u);
    }
}
