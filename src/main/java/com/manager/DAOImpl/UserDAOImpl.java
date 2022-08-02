package com.manager.DAOImpl;

import com.manager.DAO.UserDAO;
import com.manager.config.DatabaseSource;
import com.manager.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserDAOImpl implements UserDAO {

    public static DatabaseSource databaseSource = new DatabaseSource();

    @Override
    public void createUser(User user) throws SQLException {
        String query = "INSERT INTO `managerhotel`.`user` (`id`, `created_user`, `username`, `password`, `role_code`, `customer_id`, `is_deleted`, `role_id`) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        Connection connection = databaseSource.getDatasource();
        PreparedStatement prepare = connection.prepareStatement(query);
        try {
            prepare.setString(1, user.getId());
            prepare.setString(2, "SYSTEM");
            prepare.setString(3, user.getUsername());
            prepare.setString(4, user.getPassword());
            prepare.setString(5, user.getRoleCode());
            prepare.setString(6, user.getCustomerId());
            prepare.setBoolean(7, false);
            prepare.setString(8, user.getRoleId());
            prepare.execute();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUser(Map<String, String> spec) throws SQLException {
        List<User> userDtos = new ArrayList<>();
        String query = "select * from user ";
        List<String> predicates =  new ArrayList<>();
        if(!spec.isEmpty()){
             query = query + "where";
             for(Map.Entry<String, String> entry : spec.entrySet()){
                 predicates.add(" UPPER(" + entry.getKey() + ") Like " + entry.getValue());
             }
             String predicate = String.join(" AND ", predicates);
             query = query + predicate;
        }
        Connection connection =  databaseSource.getDatasource();
        PreparedStatement prepare = connection.prepareStatement(query);
        try {
            ResultSet rs = prepare.executeQuery();
            while (rs.next()){
                User userDto = new User();
                userDto.setUsername(rs.getString("username"));
                userDto.setPassword(rs.getString("password"));
                userDto.setRoleCode(rs.getString("role_code"));
                userDto.setRoleId(rs.getString("role_id"));
                userDto.setCustomerId(rs.getString("customer_id"));
                userDto.setId(rs.getString("id"));
                userDtos.add(userDto);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return userDtos;
    }



    @Override
    public User getUser(String username) throws SQLException {
        User userDto = new User();
        String query = "select * from user where username = ?";
        Connection connection =  databaseSource.getDatasource();
        PreparedStatement prepare = connection.prepareStatement(query);
        try {
            prepare.setString(1, username);
            ResultSet rs = prepare.executeQuery();
            while (rs.next()){
                userDto.setUsername(rs.getString("username"));
                userDto.setPassword(rs.getString("password"));
                userDto.setCustomerId(rs.getString("customer_id"));
                userDto.setRoleCode(rs.getString("role_code"));
                userDto.setId(rs.getString("id"));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return userDto;
    }

    @Override
    public void updateUser(Map<String, String> spec, String id) throws SQLException {

        String query = "UPDATE `managerhotel`.`user`";
        List<String> predicates =  new ArrayList<>();
        if(!spec.isEmpty()){
            query = query + "SET";
            for(Map.Entry<String, String> entry : spec.entrySet()){
                predicates.add(" " + entry.getKey() + "=" + entry.getValue());
            }
            String predicate = String.join(" , ", predicates);
            query = query + predicate + "where id = " + "'" +  id + "'";
        } else {
            return;
        }
        Connection connection = databaseSource.getDatasource();
        PreparedStatement prepare = connection.prepareStatement(query);
        try {
            prepare.execute();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(List<String> ids) {
    }

    @Override
    public List<User> getListUser() throws SQLException {

        List<User> listUser = new ArrayList<>();
        String query = "select * from user ";
        Connection connection =  databaseSource.getDatasource();
        PreparedStatement prepare = connection.prepareStatement(query);
        try {
            ResultSet rs = prepare.executeQuery();
            while (rs.next()){
                User userDto = new User();
                userDto.setUsername(rs.getString("username"));
                userDto.setPassword(rs.getString("password"));
                userDto.setRoleCode(rs.getString("role_code"));
                userDto.setRoleId(rs.getString("role_id"));
                userDto.setCustomerId(rs.getString("customer_id"));
                userDto.setId(rs.getString("id"));
                listUser.add(userDto);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return listUser;
    }
}
