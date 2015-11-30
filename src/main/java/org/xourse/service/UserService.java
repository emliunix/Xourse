package org.xourse.service;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.xourse.entity.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.xourse.resource.TeacherInfoSubmit;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * Service to manipulate teacher, student and admin data
 * Created by Liu Yuhui on 2015/11/6.
 */
@Transactional
@Component
public class UserService {
    @Resource
    SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public int saveUser(User user) {
        return (int)getSession().save(user);
    }

    public User findUser(Integer id) {
        return (User)getSession().get(User.class, id);
    }

    public User findUserByName(String username) {
        return (User) getSession().getNamedQuery("findUserByUsername")
                .setParameter("username", username)
                .uniqueResult();
    }

    public List<User> findAllUsers() {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.getNamedQuery("findAllUsers");
        return (List<User>)q.list();
    }

    public void deleteUser(int id) {
        User u = new User();
        u.setId(id);
        getSession().delete(u);
    }

    public void refreshUser(User user) {
        getSession().refresh(user);
    }

    /* Teacher */

    /**
     * Create a teacher
     * @param teacher
     * @param profile
     * @param d
     */
    public void createTeacher(Teacher teacher, TeacherProfile profile, Department d) {
        if(null == teacher)
            throw new IllegalArgumentException("teacher is null");
        Session session = getSession();
        if(d.getId() != null)
            teacher.setDepartment(d);
        if(null != profile) {
            teacher.setTeacherProfile(profile);
            session.save(profile);
        }
        session.save(teacher);
    }

    /**
     * Retrieves all teachers
     * @return
     */
    public List<Teacher> findAllTeachers() {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.getNamedQuery("findAllTeachers");
        return (List<Teacher>)q.list();
    }

    /**
     * update a teacher
     * @param teacher
     * @param profile
     */
    public void updateTeacher(Teacher teacher, TeacherProfile profile) {
        Session session = getSession();
        Teacher t = (Teacher)session.get(Teacher.class, teacher.getId());
        TeacherProfile p = (TeacherProfile)session.get(TeacherProfile.class, profile.getId());
        if(teacher.getUsername() != null) t.setUsername(t.getUsername());
        if(teacher.getPassword() != null) t.setPassword(t.getPassword());
        //if(teacher.getDepartment() != null) t.setDepartment(teacher);

        if(profile.getIdCardNumber() == null) profile.setIdCardNumber(p.getIdCardNumber());
        if(profile.getEmail() == null) profile.setEmail(p.getEmail());
        if(profile.getPoliticalStatus() == null) profile.setPoliticalStatus(p.getPoliticalStatus());
        if(profile.getResidence() == null) profile.setResidence(p.getResidence());
        if(profile.getSignature() == null) profile.setSignature(p.getSignature());
        if(profile.getTelNumber() == null) profile.setTelNumber(p.getTelNumber());
        if(profile.getTitle() == null) profile.setTitle(p.getTitle());

        teacher.setTeacherProfile(profile);

        session.merge(p);
        session.merge(t);
    }

    /**
     * Update a teachers profile
     * @param teacherId
     * @param profile
     */
    public void updateTeacherProfile(int teacherId, TeacherProfile profile) {
        Teacher t = new Teacher();
        t.setId(teacherId);
        updateTeacher(t, profile);
    }

    /* Student */
    
    public List<Student> findAllStudents() {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.getNamedQuery("findAllStudents");
        return (List<Student>)q.list();
    }

    public void updateStudent(Student student, StudentProfile profile) {
        Session session = getSession();
        Student s = (Student)session.get(Student.class, student.getId());
        StudentProfile p = (StudentProfile)session.get(StudentProfile.class, profile.getId());
        if(student.getUsername() == null) student.setUsername(s.getUsername());
        if(student.getPassword() == null) student.setPassword(s.getPassword());
        if(student.getMajorClass() == null) student.setMajorClass(s.getMajorClass());

        if(profile.getIdCardNumber() == null) profile.setIdCardNumber(p.getIdCardNumber());
        if(profile.getEmail() == null) profile.setEmail(p.getEmail());
        if(profile.getPoliticalStatus() == null) profile.setPoliticalStatus(p.getPoliticalStatus());
        if(profile.getResidence() == null) profile.setResidence(p.getResidence());
        if(profile.getSignature() == null) profile.setSignature(p.getSignature());
        if(profile.getTelNumber() == null) profile.setTelNumber(p.getTelNumber());

        student.setStudentProfile(profile);

        session.merge(profile);
        session.merge(student);
    }

    public void updateStudentProfile(int studentId, StudentProfile profile) {
        Student t = new Student();
        t.setId(studentId);
        updateStudent(t, profile);
    }

    public void updlateStudentProfile(StudentProfile profile) {
        if(0 != profile.getId())
        getSession().update(profile);
    }

    /* Admin */

    public List<Admin> findAllAdmins() {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.getNamedQuery("findAllAdmins");
        return (List<Admin>)q.list();
    }

    public void updateAdmin(Admin admin) {
        Session session = getSession();
        Admin a = (Admin)session.get(Admin.class, admin.getId());
        if(admin.getUsername() == null) admin.setUsername(a.getUsername());
        if(admin.getPassword() == null) admin.setPassword(a.getPassword());

        session.merge(admin);
    }
}
