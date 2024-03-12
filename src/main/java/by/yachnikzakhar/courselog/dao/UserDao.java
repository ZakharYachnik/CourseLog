package by.yachnikzakhar.courselog.dao;

import by.yachnikzakhar.courselog.model.User;
import by.yachnikzakhar.courselog.model.UserRole;

import java.util.List;

public interface UserDao {
    void add(User user) throws DaoException;

    User findByUsername(String username) ;

    User findByEmail(String email) throws DaoException;

    User findById(Long id) throws DaoException;

    void blockById(Long id) throws DaoException;

    void update(User user) throws DaoException;

    List<User> findAll();

    void saveUserRoles(List<UserRole> roles, User user);
}
