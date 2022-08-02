package com.manager.service;

import com.manager.dto.SearchCustomerRequest;
import com.manager.dto.SearchUserDto;
import com.manager.entity.*;

import java.awt.print.Pageable;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public interface UserService {

    User findByUserName(String username) throws SQLException;

    void create(User user) throws SQLException;

    void updateUser(User user) throws SQLException;

    Role findRoleUser(String userId);

    List<Role> findAllRole() throws SQLException;

    void createCustomer(Customer customer) throws SQLException;
    void updateCustomer(Customer customer) throws SQLException, ParseException;

    List<User> findAllUser(SearchUserDto searchUserDto) throws SQLException;

    Customer findCustomerById(String id) throws SQLException;

    List<User> getListUser() throws SQLException;

    List<Customer> findAllCustomers (SearchCustomerRequest searchCustomerRequest) throws SQLException;

    void createOrder(Order order) throws SQLException;
    void updateOrder(Order order);
    List<Order> getAllOrder(Order order) throws SQLException;
    Order getOrderById(String id) throws SQLException;

    List<OrderDetails> getOrderDetailByOrderId(String orderId) throws SQLException;
    OrderDetails getOrderDetailById(String id) throws SQLException;
    List<OrderDetails> getAllOrderDetail(OrderDetails orderDetails) throws SQLException;
    void deleteOrderDetailById(String id) throws SQLException;
    void createOrderDetail(List<OrderDetails> orderDetails) throws SQLException;
    void updateOrderDetail(OrderDetails orderDetails) throws SQLException;
}
