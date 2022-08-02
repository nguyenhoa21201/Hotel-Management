package com.manager.DAOImpl;

import com.manager.DAO.RoleDAO;
import com.manager.config.DatabaseSource;
import com.manager.entity.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDAOImpl implements RoleDAO {

    public static DatabaseSource databaseSource = new DatabaseSource();

    @Override
    public List<Role> getAllRole() throws SQLException {
        List<Role> roles = new ArrayList<>();
        String query = "select * from role";
        Connection connection = databaseSource.getDatasource();
        PreparedStatement prepare = connection.prepareStatement(query);

        try {
            ResultSet rs = prepare.executeQuery();
            while (rs.next()){
                Role role = new Role();
                role.setId(rs.getString("id"));
                role.setRoleCode(rs.getString("role_code"));
                role.setRoleName(rs.getString("role_name"));
                roles.add(role);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }

    @Override
    public Role findByUserId(String userId) {
        String query = "select * from role where id in (select role_id from user where id = ?)";
        Role role = new Role();
        try (Connection connection = databaseSource.getDatasource();
             PreparedStatement prepare = connection.prepareStatement(query)){
            prepare.setString(1, userId);
            ResultSet rs = prepare.executeQuery();
            while (rs.next()){
                role.setId(rs.getString("id"));
                role.setRoleCode(rs.getString("role_code"));
                role.setRoleName(rs.getString("role_name"));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return role;
    }

}
