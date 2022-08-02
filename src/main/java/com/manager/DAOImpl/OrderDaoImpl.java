package com.manager.DAOImpl;

import com.manager.DAO.OrderDao;
import com.manager.config.DatabaseSource;
import com.manager.entity.Order;
import com.manager.entity.Service;
import com.manager.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderDaoImpl implements OrderDao {

    public static DatabaseSource databaseSource = new DatabaseSource();

    @Override
    public List<Order> findAllOrder(Map<String, String> spec) throws SQLException {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM managerhotel.order ";
        List<String> predicates = new ArrayList<>();
        if (!spec.isEmpty()) {
            query = query + "where ";
            for (Map.Entry<String, String> entry : spec.entrySet()) {
                predicates.add(" " + entry.getKey() + " = " + entry.getValue());
            }
            String predicate = String.join(" AND ", predicates);
            query = query + predicate;
        }
        System.out.println(query);
        Connection connection = databaseSource.getDatasource();
        PreparedStatement prepare = connection.prepareStatement(query);
        try {
            ResultSet rs = prepare.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setStatus(rs.getString("status"));
                order.setId(rs.getString("id"));
                order.setOrderType(rs.getString("order_type"));
                order.setCustomerId(rs.getString("customer_id"));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public void newOrder(Order order) throws SQLException {
        String query = "INSERT INTO `managerhotel`.`order` (`id`, `created_user`, `customer_id`, `status`, `order_type`, `is_deleted`) " +
                "VALUES (?, ?, ?, ?, ?, ?);\n";
        Connection connection = databaseSource.getDatasource();
        PreparedStatement prepare = connection.prepareStatement(query);
        prepare.setString(1, order.getId());
        prepare.setString(2, "SYSTEM");
        prepare.setString(3, order.getCustomerId());
        prepare.setString(4, order.getStatus());
        prepare.setString(5, order.getOrderType());
        prepare.setBoolean(6, false);
        try {
            prepare.execute();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateOrder(Map<String, String> spec, String id) {
        String query = "UPDATE `managerhotel`.`order`";
        List<String> predicates = new ArrayList<>();
        try {
            if (!spec.isEmpty()) {
                query = query + "SET";
                for (Map.Entry<String, String> entry : spec.entrySet()) {
                    predicates.add(" " + entry.getKey() + "=" + entry.getValue());
                }
                String predicate = String.join(" , ", predicates);
                query = query + predicate + " where id = " + "'" + id + "'";
            } else {
                return;
            }
            Connection connection = databaseSource.getDatasource();
            PreparedStatement prepare = connection.prepareStatement(query);
            prepare.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Order getOrder(String id) throws SQLException {
        Order orderDto = new Order();
        String query = "select * from managerhotel.order where id = ?";
        Connection connection =  databaseSource.getDatasource();
        PreparedStatement prepare = connection.prepareStatement(query);
        prepare.setString(1, id);
        try {
            ResultSet rs = prepare.executeQuery();
            while (rs.next()){
                orderDto.setId(rs.getString("id"));
                orderDto.setOrderType(rs.getString("order_type"));
                orderDto.setStatus(rs.getString("status"));
                orderDto.setCustomerId(rs.getString("customer_id"));

            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return orderDto;
    }
}
