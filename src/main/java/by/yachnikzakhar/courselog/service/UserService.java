package by.yachnikzakhar.courselog.service;

import by.yachnikzakhar.courselog.dao.DaoException;
import by.yachnikzakhar.courselog.dao.UserDao;
import by.yachnikzakhar.courselog.dao.UserRoleDao;
import by.yachnikzakhar.courselog.dao.impl.UserRoleDaoImpl;
import by.yachnikzakhar.courselog.model.User;
import by.yachnikzakhar.courselog.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return user;
    }

}
