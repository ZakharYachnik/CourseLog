package by.yachnikzakhar.courselog.dao.impl;

import by.yachnikzakhar.courselog.dao.DaoException;
import by.yachnikzakhar.courselog.dao.UserDao;
import by.yachnikzakhar.courselog.model.User;
import by.yachnikzakhar.courselog.model.UserRole;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String SELECT_USER_BY_USERNAME = "FROM User WHERE username = :username";
    private static final String SELECT_USER_BY_ID = "FROM User WHERE id = :id";
    private static final String SELECT_USER_BY_EMAIL = "FROM User WHERE email = :email";
    private static final String CHANGE_ACTIVE_USER_BY_ID = "UPDATE User SET active = :active WHERE id = :id";
    private static final String GET_ALL_USERS = "FROM User";


    @Override
    public void add(User user) throws DaoException{
        Session currentSession = sessionFactory.getCurrentSession();

        if(findByUsername(user.getUsername()) == null){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            currentSession.persist(user);
        }else{
            throw new DaoException("User already exists");
        }
    }

    @Override
    public void update(User user) throws DaoException{
        Session currentSession = sessionFactory.getCurrentSession();

        if(findByUsername(user.getUsername()) != null){
            currentSession.merge(user);
        }else{
            throw new DaoException("User doesn't exists");
        }
    }

    @Override
    @Transactional
    public User findByUsername(String username) {
        Session currentSession = sessionFactory.getCurrentSession();

        Query<User> query = currentSession.createQuery(SELECT_USER_BY_USERNAME, User.class);
        query.setParameter("username", username);

        return query.uniqueResult();
    }

    @Override
    public User findByEmail(String email) throws DaoException {
        Session currentSession = sessionFactory.getCurrentSession();

        Query<User> query = currentSession.createQuery(SELECT_USER_BY_EMAIL, User.class);
        query.setParameter("email", email);

        return query.uniqueResult();
    }

    @Override
    public User findById(Long id) throws DaoException{
        Session currentSession = sessionFactory.getCurrentSession();

        Query<User> query = currentSession.createQuery(SELECT_USER_BY_ID, User.class);
        query.setParameter("id", id);

        return query.uniqueResult();
    }

    @Override
    public void blockById(Long id) throws DaoException{
        Session currentSession = sessionFactory.getCurrentSession();

        Query query = currentSession.createQuery(CHANGE_ACTIVE_USER_BY_ID);
        query.setParameter("id", id);
        query.setParameter("active", false);

        if(query.executeUpdate() == 0){
            throw new DaoException("User doesn't exists");
        }
    }

    @Override
    public List<User> findAll() {
        Session currentSession = sessionFactory.getCurrentSession();

        Query<User> query = currentSession.createQuery(GET_ALL_USERS, User.class);

        return query.getResultList();
    }

    @Override
    public void unblockById(Long id) throws DaoException {
        Session currentSession = sessionFactory.getCurrentSession();

        Query query = currentSession.createQuery(CHANGE_ACTIVE_USER_BY_ID);
        query.setParameter("id", id);
        query.setParameter("active", true);

        if(query.executeUpdate() == 0){
            throw new DaoException("User doesn't exists");
        }
    }
}
