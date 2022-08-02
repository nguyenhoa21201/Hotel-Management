package com.manager.DAOImpl;

import com.manager.DAO.CustomerDAO;
import com.manager.config.DatabaseSource;
import com.manager.entity.Customer;
import com.manager.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomerDAOImpl implements CustomerDAO {

    public static DatabaseSource databaseSource = new DatabaseSource();

    @Override
    public void create(Customer customer) throws SQLException {
        String query = "INSERT INTO `managerhotel`.`customer` (`id`, `created_user`, `name`, `birth_day`, `address`, `email`, `phone_number`, `type`, `is_deleted`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection =  databaseSource.getDatasource();
        CallableStatement prepare = connection.prepareCall(query);
        try{
            prepare.setString(1, customer.getId());
            prepare.setString(2, "SYSTEM");
            prepare.setString(3, customer.getName());
            prepare.setDate(4, new Date(customer.getBirthDay().getTime()));
            prepare.setString(5, customer.getAddress());
            prepare.setString(6, customer.getEmail());
            prepare.setString(7, customer.getPhone());
            prepare.setString(8, customer.getType());
            prepare.setBoolean(9, false);
            prepare.execute();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Customer findById(String id) throws SQLException {
        Customer customer = new Customer();
        String query = "select * from customer where id = ?";
        Connection connection =  databaseSource.getDatasource();
        PreparedStatement prepare = connection.prepareStatement(query);
        try {
            prepare.setString(1, id);
            ResultSet rs = prepare.executeQuery();
            while (rs.next()){
                customer.setId(rs.getString("id"));
                customer.setName(rs.getString("name"));
                customer.setType(rs.getString("type"));
                customer.setAddress(rs.getString("address"));
                customer.setEmail(rs.getString("email"));
                customer.setBirthDay(rs.getDate("birth_day"));
                customer.setPhone(rs.getString("phone_number"));
                customer.setCreatedUser(rs.getString("created_user"));
                customer.setIsDeleted(rs.getBoolean("is_deleted"));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    @Override
    public void updateById(Map<String, String> spec, String id) throws SQLException {
        String query = "UPDATE `managerhotel`.`customer`";
        List<String> predicates =  new ArrayList<>();
        if(!spec.isEmpty()){
            query = query + "SET";
            for(Map.Entry<String, String> entry : spec.entrySet()){
                predicates.add(" " + entry.getKey() + "=" + entry.getValue());
            }
            String predicate = String.join(" , ", predicates);
            query = query + predicate + " where id = " + "'" +  id + "'";
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
    public List<Customer> findAllCustomer(Map<String, String> spec) throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String query = "select * from customer ";
        List<String> predicates =  new ArrayList<>();
        if(!spec.isEmpty()){
            query = query + " where ";
            for(Map.Entry<String, String> entry : spec.entrySet()){
                if( entry.getKey().equalsIgnoreCase("name")) {
                    predicates.add(" UPPER(" + entry.getKey() + ") Like " + entry.getValue());
                    continue;
                }
                predicates.add(" UPPER(" + entry.getKey() + ") = " + entry.getValue());
            }
            String predicate = String.join(" AND ", predicates);
            query = query + predicate + "and type = 0 ";
        }
        Connection connection =  databaseSource.getDatasource();
        PreparedStatement prepare = connection.prepareStatement(query);
        try {
            ResultSet rs = prepare.executeQuery();
            while (rs.next()){
                Customer customer = new Customer();
                customer.setName(rs.getString("name"));
                customer.setPhone(rs.getString("phone_number"));
                customer.setEmail(rs.getString("email"));
                customer.setAddress(rs.getString("address"));
                customer.setId(rs.getString("id"));
                customers.add(customer);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

}
