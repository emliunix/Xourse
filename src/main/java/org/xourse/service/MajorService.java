package org.xourse.service;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.xourse.entity.Department;
import org.xourse.entity.Major;
import org.xourse.entity.MajorClass;

import java.util.List;

/**
 * Created by Liu Yuhui on 2015/11/30.
 */
@Transactional
@Repository
public class MajorService {

    @Autowired
    SessionFactory sessionFactory;

    private Session getSession() { return sessionFactory.getCurrentSession(); }

    public int create(Major m) {
        Session session = getSession();
        if(null != m.getDepartment().getId())
            session.refresh(m.getDepartment());
        return  (int)session.save(m);
    }

    public Major findById(int id) {
        Major m = (Major)getSession().get(Major.class, id);
        if(null == m) return null;
        Hibernate.initialize(m.getDepartment());
        Hibernate.initialize(m);
        return m;
    }

    public void update(Major m) {
        getSession().update(m);
    }

    public void delete(int id) {
        Major m = new Major();
        m.setId(id);
        getSession().delete(m);
    }

    public List<Major> findAll() {
        return (List<Major>)getSession().createQuery("from Major").list();
    }

    public List<MajorClass> findMajorClasses(int id) {
        Major m = (Major)getSession().get(MajorClass.class, id);
        Hibernate.initialize(m);
        return m.getClasses();
    }
}
