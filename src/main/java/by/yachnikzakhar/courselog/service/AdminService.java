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

    @Transactional
    public List<User> getAllUsers() {
        return userDao.findAll();
    }


    @Transactional
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
            user.addUserRole(userRoleDao.findByRoleName("STUDENT"));
            userDao.add(user);
            return user;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }


    @Transactional
    public User addEducator(User user) throws ServiceException {
        user.setActive(true);

        try {
            user.addUserRole(userRoleDao.findByRoleName("EDUCATOR"));
            userDao.add(user);
            return user;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Transactional
    public void blockUserById(Long id) throws ServiceException {
        try {
            userDao.blockById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Transactional
    public void unblockById(Long id) throws ServiceException {
        try {
            userDao.unblockById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
