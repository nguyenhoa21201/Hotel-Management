package com.manager.DAO;

import com.manager.dto.BusinessReport;
import com.manager.dto.BusinessReportRequest;
import com.manager.entity.OrderDetails;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface OrderDetailDao {

    void newOrderDetail(List<OrderDetails> orderDetails) throws SQLException;

    void updateOrderDetail(Map<String, String> spec, String id);

    void deleteOrderDetailById(String id) throws SQLException;

    List<OrderDetails> getOrderDetailByOrderId(String orderId) throws SQLException;

    OrderDetails getOrderDetailById(String id) throws SQLException;

    List<BusinessReport> getBusinessReport(BusinessReportRequest req) throws SQLException;

    List<OrderDetails> findAllOrderDetail(Map<String, String> spec) throws SQLException;
}
