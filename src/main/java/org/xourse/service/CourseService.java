package org.xourse.service;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.xourse.entity.*;
import org.xourse.resource.info.StuRegInfo;

import java.util.List;
import java.util.Map;

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

    public List<CoursePlan> findAllElectivePlan() {
        return getSession().createQuery("from CoursePlan where type = :type")
                .setParameter("type", CoursePlan.CourseType.ELECTIVE)
                .list();
    }

    public List<CompulsoryCourse> findCompulsoryByYear(String year) {
        Session sess = getSession();
        Query q = sess.createQuery("from CompulsoryCourse where year = :year");
        q.setString("year", year);
        List<CompulsoryCourse> cs = q.list();
        return cs;
    }

    public List<ElectiveCourse> findElectiveByTeacher(Teacher t) {
        return getSession().createQuery("from ElectiveCourse where teacher = :tea")
                .setEntity("tea", t)
                .list();
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

    /**
     * sign up students to courses
     * @param s student
     * @param ids course ids
     * @param year the year of the courses
     * @return whether operation is successful
     */
    public boolean stuSignForElectives(Student s, List<Integer> ids, String year) {
        Session sess = getSession();
        // check if this student can sign up for this year
        if(!isStudentModifiable(s, year)) {
            return false;
        }
        //
        for(int id : ids) {
            Course c = new ElectiveCourse(id);
            CourseRegistration reg = new CourseRegistration();
            reg.setStudent(s);
            reg.setCourse(c);
            sess.save(reg);
        }
//        StudentState state = new StudentState(s, year);
//        state.setModifiable(false);

        // change student modifiable state of the year to false
        StudentState state = (StudentState) sess.createQuery("from StudentState where student = :stu and year = :year")
                .setEntity("stu", s)
                .setString("year", year)
                .uniqueResult();
        if(null == state) {
            state = new StudentState(s, year);
            state.setModifiable(false);
        }
        sess.saveOrUpdate(state);

        return true;
    }

    public boolean teaSignForElectives(Teacher t, List<Integer> ids, String year) {
        if(!isTeacherModifiable(t, year)) return false;
        Session sess = getSession();
        for(int id : ids) {
            // using hql to update
//            sess.createQuery("insert into ElectiveCourse (name, year, teacher, regularRate, finalExamRate) " +
//                    "select p.name as name, :year as year, :teacher as teacher, 40 as regularRate, 60 as finalExamRate " +
//                    "from CoursePlan p where p.id = :id")
//                    .setString("year", year)
//                    .setEntity("teacher", t)
//                    .setInteger("id", id)
//                    .executeUpdate();
            // using session
            CoursePlan p = (CoursePlan) sess.load(CoursePlan.class, id);
            ElectiveCourse c = new ElectiveCourse();
            c.setName(p.getName());
            c.setTeacher(t);
            c.setYear(year);
            sess.save(c);
        }

        // change teacher modifiable state of the year to false
        TeacherState state = (TeacherState) sess.createQuery("from TeacherState where teacher = :tea and year = :year")
                .setEntity("tea", t)
                .setString("year", year)
                .uniqueResult();
        if(null == state) {
            state = new TeacherState(t, year);
            state.setModifiable(false);
        }
        state.setModifiable(false);
        sess.saveOrUpdate(state);

        return true;
    }

    /**
     * 学生是否还能够选课
     * @param s
     * @param year
     * @return
     */
    public boolean isStudentModifiable(Student s, String year) {
        StudentState state = (StudentState)getSession().createQuery("from StudentState where student = :stu and year = :year")
                .setEntity("stu", s)
                .setString("year", year)
                .uniqueResult();

        if(null == state) {
            return true;
        } else {
            return state.getModifiable();
        }
    }

    /**
     * 老师是否还能够选择要上的公选课
     * @param t
     * @param year
     * @return
     */
    public boolean isTeacherModifiable(Teacher t, String year) {
        Session sess = getSession();
        TeacherState state = (TeacherState)getSession().createQuery("from TeacherState where teacher = :tea and year = :year")
                .setEntity("tea", t)
                .setString("year", year)
                .uniqueResult();
        return null == state ? true : state.getModifiable();
    }

    public List<String> findAllCourseYears(Teacher t) {
        return getSession().createQuery("select distinct year from Course where teacher = :tea")
                .setEntity("tea", t)
                .list();
    }

    public List<Course> findCoursesByTeacher(Teacher t) {
        return getSession().createQuery("from Course where teacher = :tea")
                .setEntity("tea", t)
                .list();
    }

    public List<Course> findCoursesByTeacherAndYear(Teacher t, String year) {
        return getSession().createQuery("from Course where teacher = :tea and year = :year")
                .setEntity("tea", t)
                .setString("year", year)
                .list();
    }

    public Course findById(int cid) {
        return (Course) getSession().get(Course.class, cid);
    }

    public List<CourseRegistration> findRegistrations(Course c) {
        return getSession().createQuery("from CourseRegistration where course = :course")
                .setEntity("course", c)
                .list();
    }

    public void updateScores(Course c, List<StuRegInfo> regs) {
        Session sess = getSession();
        for(StuRegInfo r : regs) {
            sess.createQuery("update CourseRegistration " +
                    "set regularGrade = :rg, finalExamGrade = :feg, finalGrade = :fg " +
                    "where student = :stu and course = :cou")
                    .setString("rg", r.regularGrade)
                    .setString("feg", r.finalExamGrade)
                    .setString("fg", r.finalGrade)
                    .setEntity("stu", new Student(r.id))
                    .setEntity("cou", c)
                    .executeUpdate();
        }
    }

    public void setCourseFinished(Course c) {
        Course tmp = (Course) getSession().get(Course.class, c.getId());
        tmp.setFinished(true);
    }
}
