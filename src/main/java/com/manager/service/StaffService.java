package com.manager.service;

import com.manager.dto.BillResponse;
import com.manager.dto.CreateBillRequest;
import com.manager.dto.SearchServiceRequest;
import com.manager.entity.Bill;
import com.manager.entity.Room;
import com.manager.entity.Service;

import java.sql.SQLException;
import java.util.List;

public interface StaffService {
    void create(Service service) throws SQLException;

    List<Service> findAllService(SearchServiceRequest rq) throws SQLException;

    Service getServiceDetail(String id) throws SQLException;

    void updateService(Service service) throws SQLException;

    void createBill(CreateBillRequest request) throws SQLException;

    void updateBill(Bill bill);

    List<BillResponse> getAllBill(Bill bill) throws SQLException;
}
