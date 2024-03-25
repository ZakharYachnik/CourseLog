package by.yachnikzakhar.courselog.service;

import by.yachnikzakhar.courselog.dao.CourseDao;
import by.yachnikzakhar.courselog.dao.DaoException;
import by.yachnikzakhar.courselog.dao.LessonDao;
import by.yachnikzakhar.courselog.model.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class EducatorService {

    @Autowired
    private LessonDao lessonDao;

    @Autowired
    private CourseDao courseDao;

    @Transactional
    public Lesson addLesson(Lesson lesson, Long courseId) throws ServiceException {
        try {
            lesson.setCourse(courseDao.findById(courseId));
            lessonDao.add(lesson);
            return lesson;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Transactional
    public Lesson getLessonById(Long id) throws ServiceException {
        Lesson lesson = lessonDao.getById(id);

        if(lesson == null) {
            throw new ServiceException("Lesson doesn't exists");
        }

        return lesson;
    }

    @Transactional
    public void updateLesson(Long id, String lessonName, LocalDate lessonDate, String abstractText) throws ServiceException {
        try {
            Lesson lesson = lessonDao.getById(id);

            lesson.setLessonName(lessonName);
            lesson.setLessonDate(lessonDate);
            lesson.setAbstractText(abstractText);

            lessonDao.update(lesson);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Transactional
    public void deleteLesson(Long lessonId) {
        lessonDao.delete(lessonId);
    }
}
