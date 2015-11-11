package org.liu.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.liu.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Liu Yuhui on 2015/11/6.
 */
@Transactional
@Component
public class UserService {

    @Resource
    SessionFactory sessionFactory;

    public User findUser(long id) {
        Session session = sessionFactory.getCurrentSession();
        Object o = session.get(User.class, id);
        return (User)o;
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

    public void deleteUser(long id) {
        Session session = sessionFactory.getCurrentSession();
        User u = new User();
        u.setId(id);
        session.delete(u);
    }

    public List<User> getUsers() {
        Session session = sessionFactory.getCurrentSession();
        List result = session.getNamedQuery("getAllUsers").list();
        return result;
    }
}
