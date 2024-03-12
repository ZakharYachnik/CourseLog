package by.yachnikzakhar.courselog.service;

import by.yachnikzakhar.courselog.dao.DaoException;
import by.yachnikzakhar.courselog.dao.UserDao;
import by.yachnikzakhar.courselog.dao.UserRoleDao;
import by.yachnikzakhar.courselog.model.User;
import by.yachnikzakhar.courselog.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRoleDao userRoleDao;

    public List<User> getAllUsers() {
        return userDao.findAll();
    }


    public void blockById(Long id) throws ServiceException {
        try {
            userDao.blockById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }


    @Transactional
    public User addStudent(User user) throws ServiceException {
        user.setActive(true);

        try {
            List<UserRole> roles = List.of(userRoleDao.findByRoleName("STUDENT"));

            userDao.add(user);
            userDao.saveUserRoles(roles, user);

            return user;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
