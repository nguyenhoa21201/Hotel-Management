package com.manager.DAO;

import com.manager.entity.Customer;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface CustomerDAO {
    void create(Customer customer) throws SQLException;

    Customer findById(String id) throws SQLException;

    void updateById(Map<String, String> spec, String id) throws SQLException;

    List<Customer> findAllCustomer(Map<String, String> spec) throws SQLException;
}
