package com.manager.DAO;

import com.manager.entity.Role;
import com.manager.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface RoleDAO {
    List<Role> getAllRole() throws SQLException;
    Role findByUserId(String userId);
}
