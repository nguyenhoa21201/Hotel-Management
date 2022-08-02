package com.manager.DAOImpl;

import com.manager.DAO.BillDetailDAO;
import com.manager.config.DatabaseSource;
import com.manager.entity.BillDetails;
import com.manager.entity.OrderDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BillDetailDAOImpl implements BillDetailDAO {

    public static DatabaseSource databaseSource = new DatabaseSource();

    @Override
    public void newBillDetail(List<BillDetails> BillDetails) throws SQLException {
        String query = "INSERT INTO `managerhotel`.`billdetails` (`id`, `created_user`, `bill_id`, `ref_id`," +
                " `ref_type`, `price_ref`, `name_ref`, `unit`, `amount`, `is_deleted`) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?);";
        Connection connection = databaseSource.getDatasource();
        PreparedStatement prepare = connection.prepareStatement(query);
        for (BillDetails detail : BillDetails) {
            prepare.setString(1, detail.getId());
            prepare.setString(2, detail.getCreatedUser());
            prepare.setString(3, detail.getBillId());
            prepare.setString(4, detail.getRefId());
            prepare.setString(5, detail.getRefType());
            prepare.setDouble(6, detail.getPriceRef());
            prepare.setString(7, detail.getNameRef());
            prepare.setString(8, detail.getUnit());
            prepare.setString(9, detail.getAmount());
            prepare.setBoolean(10, false);
            prepare.addBatch();
        }
        try {
            prepare.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateBillDetail(Map<String, String> spec, String id) {
        String query = "UPDATE `managerhotel`.`order`";
        List<String> predicates = new ArrayList<>();
        try {
            if (!spec.isEmpty()) {
                query = query + "SET";
                for (Map.Entry<String, String> entry : spec.entrySet()) {
                    predicates.add(" " + entry.getKey() + "=" + entry.getValue());
                }
                String predicate = String.join(" , ", predicates);
                query = query + predicate + "where id = " + "'" + id + "'";
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
    public List<BillDetails> getBillByOrderId(String billId) throws SQLException {
        String query = "select * from billdetails where bill_id = ?";
        List<BillDetails> billDetails = new ArrayList<>();
        Connection connection = databaseSource.getDatasource();
        PreparedStatement prepare = connection.prepareStatement(query);
        try {
            prepare.setString(1, billId);
            ResultSet rs = prepare.executeQuery();

            while (rs.next()) {
                BillDetails billDetail = new BillDetails();
                billDetail.setId(rs.getString("id"));
                billDetail.setBillId(rs.getString("bill_id"));
                billDetail.setAmount(rs.getString("amount"));
                billDetail.setNameRef(rs.getString("name_ref"));
                billDetail.setPriceRef(rs.getDouble("price_ref"));
                billDetail.setRefType(rs.getString("ref_type"));
                billDetail.setUnit(rs.getString("unit"));
                billDetails.add(billDetail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return billDetails;
    }

    @Override
    public BillDetails getBillDetailById(String id) throws SQLException {
        String query = "select * from billdetails where id = ?";
        BillDetails billDetail = new BillDetails();
        Connection connection = databaseSource.getDatasource();
        PreparedStatement prepare = connection.prepareStatement(query);
        try {
            prepare.setString(1, id);
            ResultSet rs = prepare.executeQuery();
            while (rs.next()) {
                billDetail.setId(rs.getString("id"));
                billDetail.setBillId(rs.getString("bill_id"));
                billDetail.setAmount(rs.getString("amount"));
                billDetail.setNameRef(rs.getString("name_ref"));
                billDetail.setPriceRef(rs.getDouble("price_ref"));
                billDetail.setRefType(rs.getString("ref_type"));
                billDetail.setUnit(rs.getString("unit"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return billDetail;
    }
}
