package by.yachnikzakhar.courselog.dao.impl;

import by.yachnikzakhar.courselog.dao.CourseDao;
import by.yachnikzakhar.courselog.dao.DaoException;
import by.yachnikzakhar.courselog.model.Course;
import by.yachnikzakhar.courselog.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CourseDaoImpl implements CourseDao {

    @Autowired
    private SessionFactory sessionFactory;

    private static final String SELECT_COURSE_BY_ID = "FROM Course WHERE id = :id";
    private static final String SELECT_COURSE_BY_COURSE_NAME = "FROM Course WHERE courseName = :courseName";
    private static final String SELECT_COURSE_BY_GROUP_CODE = "FROM Course WHERE groupCode = :groupCode";
    private static final String COMPLETE_COURSE_BY_ID = "UPDATE Course SET status = :status WHERE id = :id";
    private static final String SELECT_ALL_COURSES = "FROM Course";

    @Override
    public void add(Course course) throws DaoException {
        Session currentSession = sessionFactory.getCurrentSession();

        if (findCourseByGroupCode(course.getGroupCode()) == null) {
            currentSession.persist(course);
        } else {
            throw new DaoException("Course already exists");
        }
    }

    @Override
    public Course findCourseByGroupCode(String groupCode) throws DaoException {
        Session currentSession = sessionFactory.getCurrentSession();

        Query<Course> query = currentSession.createQuery(SELECT_COURSE_BY_GROUP_CODE, Course.class);
        query.setParameter("groupCode", groupCode);

        return query.uniqueResult();
    }

    @Override
    public Course findByCourseName(String courseName) throws DaoException {
        Session currentSession = sessionFactory.getCurrentSession();

        Query<Course> query = currentSession.createQuery(SELECT_COURSE_BY_COURSE_NAME, Course.class);
        query.setParameter("courseName", courseName);

        return query.uniqueResult();
    }

    @Override
    public Course findById(Long id) throws DaoException {
        Session currentSession = sessionFactory.getCurrentSession();

        Query<Course> query = currentSession.createQuery(SELECT_COURSE_BY_ID, Course.class);
        query.setParameter("id", id);

        return query.uniqueResult();
    }

    @Override
    public void completeById(Long id) throws DaoException {
        Session currentSession = sessionFactory.getCurrentSession();

        Query query = currentSession.createQuery(COMPLETE_COURSE_BY_ID);
        query.setParameter("id", id);
        query.setParameter("status", "COMPLETED");

        if(query.executeUpdate() == 0){
            throw new DaoException("Course doesn't exists");
        }
    }

    @Override
    public void update(Course course) throws DaoException {
        Session currentSession = sessionFactory.getCurrentSession();

        if(findCourseByGroupCode(course.getGroupCode()) != null){
            currentSession.merge(course);
        }else{
            throw new DaoException("Course doesn't exists");
        }
    }

    @Override
    public List<Course> getAllCourses() throws DaoException {
        Session currentSession = sessionFactory.getCurrentSession();

        Query<Course> query = currentSession.createQuery(SELECT_ALL_COURSES, Course.class);

        return query.list();
    }


}
