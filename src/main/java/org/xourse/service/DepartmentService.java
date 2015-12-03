package org.xourse.service;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.xourse.entity.Department;
import org.xourse.entity.Major;
import org.xourse.entity.Teacher;

import java.util.List;
import java.util.Map;

/**
 * Created by Liu Yuhui on 2015/11/30.
 */
@Transactional
@Repository
public class DepartmentService {

    @Autowired
    SessionFactory sessionFactory;

    private Session getSession() { return sessionFactory.getCurrentSession(); }

    public int create(Department d) {
        return (int)getSession().save(d);
    }

    public Department findById(int id) {
        return (Department)getSession().get(Department.class, id);
    }

    public void update(Department d) {
        getSession().update(d);
    }

    public void delete(int id) {
        Department d = new Department();
        d.setId(id);
        getSession().delete(d);
    }

    public List<Department> findAll() {
        return (List<Department>)getSession().createQuery("from Department").list();
    }

    public List<Major> getMajors(int id) {
        Session sess = getSession();
        Department d = (Department)sess.get(Department.class, id);
        if(null == d)
            return null;
        Hibernate.initialize(d.getMajors());
        return d.getMajors();
    }

    public List<Teacher> getTeachers(int id) {
        Department d = (Department)getSession().get(Department.class, id);
        if(null == d)
            return null;
        Hibernate.initialize(d.getTeachers());
        return d.getTeachers();
    }
}
