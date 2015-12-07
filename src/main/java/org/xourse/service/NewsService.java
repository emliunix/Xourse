package org.xourse.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.xourse.entity.News;

import java.util.List;

/**
 * Created by Liu Yuhui on 2015/12/7.
 */
@Repository
@Transactional
public class NewsService {
    @Autowired
    SessionFactory sessionFactory;

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public List<News> findAll() {
        return getSession().createQuery("from News order by ctime desc ").list();
    }

    public int create(News n) {
        return (int)getSession().save(n);
    }

    public void update(News news) {
        Session sess = getSession();
        News n = (News) sess.load(News.class, news.id);
        if(null != news.title) n.title = news.title;
        if(null != news.content) n.content = news.content;
        sess.flush();
    }

    public void remove(News n) {
        getSession().delete(n);
    }
}
