package org.liu.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.liu.persistence.User;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;

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
}
