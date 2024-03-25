package by.yachnikzakhar.courselog.dao.impl;

import by.yachnikzakhar.courselog.dao.DaoException;
import by.yachnikzakhar.courselog.dao.LessonDao;
import by.yachnikzakhar.courselog.model.Lesson;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LessonDaoImpl implements LessonDao {

    @Autowired
    private SessionFactory sessionFactory;

    private static final String SELECT_LESSON_BY_ID = "FROM Lesson WHERE id = :id";
    private static final String SELECT_LESSON_BY_LESSON_NAME = "FROM Lesson WHERE lessonName = :lessonName";

    @Override
    public Lesson add(Lesson lesson) throws DaoException {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.persist(lesson);
        return lesson;
    }

    @Override
    public Lesson getById(Long id) {
        Session currentSession = sessionFactory.getCurrentSession();

        Query<Lesson> query = currentSession.createQuery(SELECT_LESSON_BY_ID, Lesson.class);
        query.setParameter("id", id);

        return query.uniqueResult();
    }

    @Override
    public Lesson update(Lesson lesson) throws DaoException {
        Session currentSession = sessionFactory.getCurrentSession();

        currentSession.merge(lesson);

        return lesson;
    }

    @Override
    public Lesson findByLessonName(String lessonName) {
        Session currentSession = sessionFactory.getCurrentSession();

        Query<Lesson> query = currentSession.createQuery(SELECT_LESSON_BY_LESSON_NAME, Lesson.class);
        query.setParameter("lessonName", lessonName);

        return query.uniqueResult();
    }

    @Override
    public void delete(Long lessonId) {
        Session currentSession = sessionFactory.getCurrentSession();
        Lesson lesson = currentSession.get(Lesson.class, lessonId);
        currentSession.remove(lesson);
    }
}
