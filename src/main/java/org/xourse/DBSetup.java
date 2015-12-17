package org.xourse;

import com.google.common.reflect.ClassPath;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.metamodel.SessionFactoryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.xourse.entity.*;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Properties;

/**
 * Created by Liu Yuhui on 2015/12/6.
 */
public class DBSetup {
    public static void main(String[] args) {
        Configuration cfg = new Configuration();
        Properties props = new Properties();
        try {
            props.load(DBSetup.class.getClassLoader().getResourceAsStream("hibernate.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        props.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/xourse");
        cfg.addProperties(props);
        try {
            for(ClassPath.ClassInfo info : ClassPath.from(DBSetup.class.getClassLoader()).getTopLevelClasses("org.xourse.entity")) {
                Class clazz = Class.forName(info.getName());
                if(clazz.isAnnotationPresent(Entity.class)) {
                    cfg.addAnnotatedClass(clazz);
                }
            }
//                cfg.addPackage("org.xourse.entity");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        SchemaExport export = new SchemaExport(cfg);
        //cfg.configure();
        export.setOutputFile("init.sql");
        export.execute(true, true, false, false);

        // insert entities
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        Department ci = new Department("计算机与信息工程学院");
        Department math = new Department("数学院");
        Major se = new Major("软件工程");
        Major cs = new Major("计算机科学");
        se.setDepartment(ci);
        cs.setDepartment(ci);
        MajorClass se13 = new MajorClass("13软工", "2013");
        MajorClass cs13_1 = new MajorClass("13计科1", "2013");
        MajorClass cs13_2 = new MajorClass("13计科2", "2013");
        se13.setMajor(se);
        cs13_1.setMajor(cs);
        cs13_2.setMajor(cs);
        for(Object o : new Object[] {cs13_1, cs13_2, cs, se13, se, ci, math}) {
            session.save(o);
        }

        // users
        Student lyh = new Student("2013221104220024", "刘宇辉", "2013", "男", "身份证号", "电话号码",
                "185127913@qq.com", "武汉", "团员", "老子刘宇辉");
        Student kb = new Student("2013221104220023", "柯波", "2013", "男", "身份证号", "电话号码",
                "youxiang@qq.com", "武汉", "团员", "柯逼");
        Teacher tea1 = new Teacher();
        tea1.setTeacherProfile(new TeacherProfile());
        tea1.setUsername("1234567");
        tea1.setName("老师1");
        tea1.setYear("2008");
        tea1.setTeacherId(tea1.getUsername());
        tea1.getTeacherProfile().setGender("男");

        lyh.setMajorClass(se13);
        kb.setMajorClass(se13);
        tea1.setDepartment(ci);

        Admin admin = new Admin("admin", "admin");

        for(User o : new User[] {lyh, kb, tea1}) {
            o.setPassword("password");
            session.save(o);
        }

        session.save(admin);

        // courses

        CoursePlan p1 = new CoursePlan("C语言", CoursePlan.CourseType.COMPULSORY),
                p2 = new CoursePlan("PS大法", CoursePlan.CourseType.ELECTIVE);

        CompulsoryCourse clang = new CompulsoryCourse("C语言", "2013-2014-1");
        ElectiveCourse psPower = new ElectiveCourse("PS大法", "2013-2014-2");

        clang.setMajorClass(se13);
        clang.setTeacher(tea1);
        psPower.setTeacher(tea1);

        for(Object o : new Object[] {p1, p2, clang, psPower}) session.save(o);

        for (Course c : new Course[] { clang, psPower }) {
            for (Student s : new Student[]{lyh, kb}) {
                CourseRegistration reg = new CourseRegistration();
                reg.setStudent(s);
                reg.setCourse(c);
                session.save(reg);
            }
        }

        // course plans

        CoursePlan cp1 = new CoursePlan("篮球", CoursePlan.CourseType.ELECTIVE);
        CoursePlan cp2 = new CoursePlan("羽毛球", CoursePlan.CourseType.ELECTIVE);
        CoursePlan cp3 = new CoursePlan("健美操", CoursePlan.CourseType.ELECTIVE);

        session.save(cp1);
        session.save(cp2);
        session.save(cp3);

        session.flush();
        session.getTransaction().commit();
    }
}
