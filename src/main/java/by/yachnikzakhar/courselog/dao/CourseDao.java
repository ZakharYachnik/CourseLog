package by.yachnikzakhar.courselog.dao;

import by.yachnikzakhar.courselog.model.Course;

import java.util.List;

public interface CourseDao {

    void add(Course course) throws DaoException;

    Course findByCourseName(String courseName) throws DaoException;

    Course findById(Long id) throws DaoException;

    void completeById(Long id) throws DaoException;

    void update(Course course) throws DaoException;

    Course findCourseByGroupCode(String groupCode) throws DaoException;

    List<Course> getAllCourses() throws DaoException;
}
