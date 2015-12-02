package org.xourse.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.xourse.entity.CoursePlan;

import java.util.List;

/**
 * Course plan repository
 * Created by Liu Yuhui on 2015/12/2.
 */
@Transactional
@Repository
public class CoursePlanService {
    @Autowired
    SessionFactory sessionFactory;

    private Session getSession() { return sessionFactory.getCurrentSession(); }

    public CoursePlan create(CoursePlan p) {
        Session sess = getSession();
        sess.save(p);
        return p;
    }

    public List<CoursePlan> findAll() {
        return (List<CoursePlan>)getSession().createQuery("from CoursePlan ").list();
    }

    public CoursePlan findById(int id) {
        return (CoursePlan)getSession().get(CoursePlan.class, id);
    }

    public List<CoursePlan> findAllCompulsory()  {
        return (List<CoursePlan>)getSession().createQuery("from CoursePlan where type = CoursePlan.CourseType.COMPULSORY").list();
    }

    public List<CoursePlan> findAllElective() {
        return (List<CoursePlan>)getSession().createQuery("from CoursePlan where type = CoursePlan.CourseType.COMPULSORY").list();
    }

    public CoursePlan update(CoursePlan plan) {
        Session sess = getSession();
        CoursePlan p = (CoursePlan)sess.load(CoursePlan.class, plan.getId());
        if(null != plan.getName()) p.setName(plan.getName());
        if(null != plan.getType()) p.setType(plan.getType());
        sess.flush();
        return p;
    }

    public void delete(CoursePlan p) {
        getSession().delete(p);
    }

    public void delete(int id) {
        CoursePlan p = new CoursePlan();
        p.setId(id);
        delete(p);
    }
}
