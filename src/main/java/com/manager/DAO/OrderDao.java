package com.manager.DAO;

import com.manager.entity.Order;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface OrderDao {

    List<Order> findAllOrder(Map<String, String> spec) throws SQLException;

    void newOrder(Order order) throws SQLException;

    void updateOrder(Map<String, String> spec, String id);

    Order getOrder(String id) throws SQLException;

}
