package com.manager.DAO;

import com.manager.entity.Bill;
import com.manager.entity.Order;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface BillDAO {
    List<Bill> findAlBill(Map<String, String> spec) throws SQLException;

    void newBill(Bill bill) throws SQLException;

    void updateBill(Map<String, String> spec, String id);

    Bill getBill(String id) throws SQLException;

}
