package by.yachnikzakhar.courselog.dao.impl;

import by.yachnikzakhar.courselog.dao.UserRoleDao;
import by.yachnikzakhar.courselog.model.UserRole;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserRoleDaoImpl implements UserRoleDao {

    @Autowired
    private SessionFactory sessionFactory;

    private static final String SELECT_USER_ROLE_BY_ROLE_NAME = "FROM UserRole WHERE roleName = :roleName";

    @Override
    @Transactional
    public UserRole findByRoleName(String roleName) {
        Session currentSession = sessionFactory.getCurrentSession();

        Query<UserRole> query = currentSession.createQuery(SELECT_USER_ROLE_BY_ROLE_NAME, UserRole.class);
        query.setParameter("roleName", roleName);

        return query.uniqueResult();
    }
}
