package org.xourse.service;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.xourse.entity.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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

    private static Logger logger = Logger.getLogger(UserService.class);

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

    public User updateUser(User user) {
        Session sess = getSession();
        User u = (User) sess.load(User.class, user.getId());
        if(null != user.getUsername()) u.setUsername(user.getUsername());
        if(null != user.getPassword()) u.setPassword(user.getPassword());
        return u;
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

    public void createTeacher(Teacher teacher) {
        if(null == teacher.getTeacherProfile())
            teacher.setTeacherProfile(new TeacherProfile());
        getSession().save(teacher);
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
     */
    public Teacher updateTeacher(Teacher teacher) {
        Session session = getSession();
        Teacher t = (Teacher)session.load(Teacher.class, teacher.getId());
        mergeInTeacher(t, teacher);
        session.flush();
        return t;
    }

    private void mergeInTeacher(Teacher dest, Teacher src) {
        if(src.getUsername() != null) dest.setUsername(src.getUsername());
        if(src.getPassword() != null) dest.setPassword(src.getPassword());
        if(src.getDepartment() != null) dest.setDepartment(src.getDepartment());
        if(null != src.getTeacherProfile()) {
            if(null == dest.getTeacherProfile()) {
                dest.setTeacherProfile(new TeacherProfile());
                logger.warn("profile doesn't exist for a teacher, this is an error");
            }
            mergeInTeacherProfile(dest.getTeacherProfile(), src.getTeacherProfile());
        }
    }

    private void mergeInTeacherProfile(TeacherProfile dest, TeacherProfile src) {
        if(src.getIdCardNumber() != null) dest.setIdCardNumber(src.getIdCardNumber());
        if(src.getEmail() != null) dest.setEmail(src.getEmail());
        if(src.getPoliticalStatus() != null) dest.setPoliticalStatus(src.getPoliticalStatus());
        if(src.getResidence() != null) dest.setResidence(src.getResidence());
        if(src.getSignature() != null) dest.setSignature(src.getSignature());
        if(src.getTelNumber() != null) dest.setTelNumber(src.getTelNumber());
        if(src.getTitle() != null) dest.setTitle(src.getTitle());
    }

    /* Student */

    public void createStudent(Student student) {
        Session session = getSession();
        if(null == student.getStudentProfile())
            student.setStudentProfile(new StudentProfile());
        session.save(student);
    }

    public List<Student> findAllStudents() {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.getNamedQuery("findAllStudents");
        return (List<Student>)q.list();
    }

    public Student updateStudent(Student student) {
        Session session = getSession();
        Student s = (Student)session.load(Student.class, student.getId());

        // apply updates
        mergeInStudent(s, student);

        session.flush();
        return s;
    }

    private void mergeInStudent(Student target, Student source) {
        if(source.getUsername() != null) target.setUsername(source.getUsername());
        if(source.getPassword() != null) target.setPassword(source.getPassword());
        if(source.getMajorClass() != null) target.setMajorClass(source.getMajorClass());
        if(null != source.getStudentProfile()) {
            if(null == target.getStudentProfile()) {
                target.setStudentProfile(new StudentProfile());
                logger.warn("profile doesn't exist for a student, this is an error");
            }
            mergeInStudentProfile(target.getStudentProfile(), source.getStudentProfile());
        }
    }

    private void mergeInStudentProfile(StudentProfile dest, StudentProfile src) {
        if(src.getIdCardNumber() != null) dest.setIdCardNumber(src.getIdCardNumber());
        if(src.getEmail() != null) dest.setEmail(src.getEmail());
        if(src.getPoliticalStatus() != null) dest.setPoliticalStatus(src.getPoliticalStatus());
        if(src.getResidence() != null) dest.setResidence(src.getResidence());
        if(src.getSignature() != null) dest.setSignature(src.getSignature());
        if(src.getTelNumber() != null) dest.setTelNumber(src.getTelNumber());
    }

    /* Admin */

    public List<Admin> findAllAdmins() {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.getNamedQuery("findAllAdmins");
        return (List<Admin>)q.list();
    }

    public void createAdmin(Admin a) {
        Session sess = getSession();
        sess.save(a);
    }

    public Admin updateAdmin(Admin admin) {
        Session sess = getSession();
        Admin a = (Admin) sess.load(Admin.class, admin.getId());
        if(null != admin.getUsername()) a.setUsername(admin.getUsername());
        if(null != admin.getPassword()) a.setPassword(admin.getPassword());
        return a;
    }

}
