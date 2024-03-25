package by.yachnikzakhar.courselog.dao;

import by.yachnikzakhar.courselog.model.Lesson;

public interface LessonDao {
    Lesson add(Lesson lesson) throws DaoException;

    Lesson getById(Long id);

    Lesson update(Lesson lesson) throws DaoException;

    Lesson findByLessonName(String lessonName);

    void delete(Long lessonId);
}
