package org.xourse.service;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Compulsory Course service
 * Created by Liu Yuhui on 2015/12/2.
 */
@Transactional
@Repository
public class CompulsoryCourseService {
    @Autowired
    SessionFactory sessionFactory;


}
