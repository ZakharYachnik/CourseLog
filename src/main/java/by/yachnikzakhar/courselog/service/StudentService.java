package by.yachnikzakhar.courselog.service;

import by.yachnikzakhar.courselog.dao.CourseDao;
import by.yachnikzakhar.courselog.dao.DaoException;
import by.yachnikzakhar.courselog.model.Course;
import by.yachnikzakhar.courselog.model.User;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private CourseDao courseDao;

}
