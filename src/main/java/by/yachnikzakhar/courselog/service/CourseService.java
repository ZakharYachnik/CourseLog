package by.yachnikzakhar.courselog.service;

import by.yachnikzakhar.courselog.dao.CourseDao;
import by.yachnikzakhar.courselog.dao.DaoException;
import by.yachnikzakhar.courselog.dao.UserDao;
import by.yachnikzakhar.courselog.model.Course;
import by.yachnikzakhar.courselog.model.User;
import by.yachnikzakhar.courselog.model.UserRole;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private UserDao userDao;

    @Transactional
    public Course addCourse(String courseName, String groupCode, String usernames) throws ServiceException {
        Course course = new Course(courseName, groupCode);
        course.setStatus("ACTIVE");

        List<User> users = getUsersByUsernames(usernames);

        try {
            courseDao.add(course);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }

        for(User user : users){
            user.getCourses().add(course);
        }

        return course;
    }

    private List<User> getUsersByUsernames(String usernames) throws ServiceException {
        String[] usernamesList = usernames.split(",");
        List<User> users = new ArrayList<>();
        for (String username : usernamesList) {
            User user = userDao.findByUsername(username);

            if(user == null){
                throw new ServiceException("User not found");
            }
            users.add(user);
        }
        return users;
    }

    @Transactional
    public Course getCourseByName(String name) throws ServiceException {
        try {
            return courseDao.findByCourseName(name);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }


    @Transactional
    public List<Course> getAllCourses() throws ServiceException {
        try {
            return courseDao.getAllCourses();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Transactional
    public Course getCourseById(Long id) throws ServiceException {
        try {
            return courseDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Transactional
    public String getEducatorUsername(Course course) {
        for (User user : course.getUsers()) {
            for (UserRole userRole : user.getUserRoles()) {
                if (userRole.getRoleName().equals("EDUCATOR")) {
                    return user.getUsername();
                }
            }
        }
        return null;
    }


    @Transactional
    public String getUsernames(Course course) {
        StringBuilder usernames = new StringBuilder();
        for (User user : course.getUsers()) {
            for(UserRole userRole : user.getUserRoles()){
                if(userRole.getRoleName().equals("STUDENT")){
                    usernames.append(user.getUsername()).append(",");
                }
            }
        }
        usernames.setLength(usernames.length() - 1);
        return usernames.toString();
    }

    @Transactional
    public Course changeCourse(
            String courseName,
            String groupCode,
            String usernames,
            String educator
    ) throws ServiceException {
        Course course = null;
        try {
            course = courseDao.findCourseByGroupCode(groupCode);
            course.setCourseName(courseName);

            usernames = usernames + "," + educator;
            List<User> users = getUsersByUsernames(usernames);
            course.setUsers(users);

            courseDao.update(course);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }

        return course;
    }

    @Transactional
    public void completeById(Long id) throws ServiceException {
        try {
            courseDao.completeById(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
