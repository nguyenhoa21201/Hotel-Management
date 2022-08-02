package com.manager.DAO;

import com.manager.entity.BillDetails;
import com.manager.entity.OrderDetails;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface BillDetailDAO {

    void newBillDetail(List<BillDetails> BillDetails) throws SQLException;

    void updateBillDetail(Map<String, String> spec, String id);

    List<BillDetails> getBillByOrderId(String billId) throws SQLException;

    BillDetails getBillDetailById(String id) throws SQLException;

}
