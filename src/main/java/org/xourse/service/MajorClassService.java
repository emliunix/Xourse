package org.xourse.service;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.xourse.entity.MajorClass;
import org.xourse.entity.Student;

import java.util.List;

/**
 * Major class service
 * Created by Liu Yuhui on 2015/11/30.
 */
@Transactional
@Repository
public class MajorClassService {

    @Autowired
    SessionFactory sessionFactory;

    private Session getSession() { return sessionFactory.getCurrentSession(); }

    public int create(MajorClass m) {
        return (int)getSession().save(m);
    }

    public MajorClass findById(int id) {
        MajorClass c = (MajorClass)getSession().get(MajorClass.class, id);
        if(null == c)
            return null;
        Hibernate.initialize(c);
        return c;
    }

    public void update(MajorClass c) {
        getSession().update(c);
    }

    public void delete(int id) {
        MajorClass c = new MajorClass();
        c.setId(id);
        getSession().delete(c);
    }

    public List<MajorClass> findAll() {
        return (List<MajorClass>)getSession().createQuery("from MajorClass").list();
    }

    public List<Student> findStudents(int id) {
        MajorClass c = (MajorClass)getSession().get(MajorClass.class, id);
        Hibernate.initialize(c.getStudents());
        return c.getStudents();
    }
}
