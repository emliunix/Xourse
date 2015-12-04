package org.xourse.service;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.xourse.entity.*;

import java.util.List;

/**
 * Compulsory Course service
 * Created by Liu Yuhui on 2015/12/2.
 */
@Transactional
@Repository
public class CourseService {
    @Autowired
    SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public List<Course> findAll() {
        return getSession().createQuery("from Course ").list();
    }

    public List<CompulsoryCourse> findAllCompulsory() {
        return getSession().createQuery("from CompulsoryCourse ").list();
    }

    public List<ElectiveCourse> findAllElective() {
        return getSession().createQuery("from ElectiveCourse ").list();
    }

    public List<CompulsoryCourse> findCompulsoryByYear(String year) {
        Session sess = getSession();
        Query q = sess.createQuery("from CompulsoryCourse where year = :year");
        q.setString("year", year);
        List<CompulsoryCourse> cs = q.list();
        return cs;
    }

    public List<ElectiveCourse> findElectiveByYear(String year) {
        Session sess = getSession();
        return sess.createQuery("from ElectiveCourse where year = :year")
                .setString("year", year).list();
    }

    public int createCompulsory(CompulsoryCourse course) {
        return (int)getSession().save(course);
    }

    public int createElective(ElectiveCourse course) {
        return (int) getSession().save(course);
    }

    public CompulsoryCourse updateCompulsory(CompulsoryCourse course) {
        Session sess = getSession();
        CompulsoryCourse c = (CompulsoryCourse)sess.get(CompulsoryCourse.class, course.getId());
        if(null == c)
            return null;
        mergeInCourse(c, course);
        if(null != course.getMajorClass()) c.setMajorClass(course.getMajorClass());
        sess.flush();
        return c;
    }

    public ElectiveCourse updateElective(ElectiveCourse course) {
        Session sess = getSession();
        ElectiveCourse c = (ElectiveCourse) sess.get(ElectiveCourse.class, course.getId());
        if(null == c)
            return null;
        mergeInCourse(c, course);
        sess.flush();
        return c;
    }

    private void mergeInCourse(Course dest, Course src) {
        if(null != src.getRegularRate()) dest.setRegularRate(src.getRegularRate());
        if(null != src.getFinalExamRate()) dest.setFinalExamRate(src.getFinalExamRate());
        if(null != src.getName()) dest.setName(src.getName());
        if(null != src.getYear()) dest.setYear(src.getYear());
        if(null != src.getTeacher()) dest.setTeacher(src.getTeacher());
    }

    public StudentState findStudentState(Student s, String year) {
        return (StudentState) getSession()
                .createQuery("from StudentState where student = :stu and year = :year")
                .setEntity("stu", s)
                .setString("year", year)
                .uniqueResult();
    }

    public List<String> findAllElectiveYears() {
        return getSession()
                .createQuery("select distinct e.year as year from ElectiveCourse e order by e.year desc ")
                .list();
    }

    public List<String> findAllCompulsoryYears() {
        return getSession()
                .createQuery("select distinct e.year as year from ElectiveCourse e order by e.year desc ")
                .list();
    }
}
