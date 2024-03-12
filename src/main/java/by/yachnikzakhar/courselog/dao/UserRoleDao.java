package by.yachnikzakhar.courselog.dao;

import by.yachnikzakhar.courselog.model.UserRole;
import org.springframework.stereotype.Repository;


public interface UserRoleDao {
    UserRole findByRoleName(String roleName);
}
