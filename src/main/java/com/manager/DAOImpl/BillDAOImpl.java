package com.manager.DAOImpl;


import com.manager.DAO.BillDAO;
import com.manager.config.DatabaseSource;
import com.manager.config.StringUtil;
import com.manager.entity.Bill;
import com.manager.entity.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BillDAOImpl implements BillDAO {

    public static DatabaseSource databaseSource = new DatabaseSource();

    @Override
    public List<Bill> findAlBill(Map<String, String> spec) throws SQLException {
        List<Bill> bills = new ArrayList<>();
        String query = "SELECT * FROM managerhotel.bill ";
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
                Bill bill = new Bill();
                bill.setId(rs.getString("id"));
                bill.setCreatedUser(rs.getString("created_user"));
                bill.setInvoiceDate(rs.getTimestamp("invoice_date"));
                bill.setCheckinDate(rs.getTimestamp("checkin_date"));
                bill.setCheckoutDate(rs.getTimestamp("checkout_date"));
                bill.setCustomerId(rs.getString("customer_id"));
                bill.setStatus(rs.getString("status"));
                bills.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bills;
    }

    @Override
    public void newBill(Bill bill) throws SQLException {
        String query = "INSERT INTO `managerhotel`.`bill` (`id`, `created_user`, `invoice_date`," +
                " `checkin_date`, `checkout_date`, `customer_id`, `people_number`, `is_deleted`, `status`, " +
                "`checkin_month`, `checkout_month`, `checkin_year`, `checkout_year`)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection = databaseSource.getDatasource();
        PreparedStatement prepare = connection.prepareStatement(query);
        prepare.setString(1, bill.getId());
        prepare.setString(2, bill.getCreatedUser());
        prepare.setTimestamp(3, bill.getInvoiceDate());
        prepare.setTimestamp(4, bill.getCheckinDate());
        prepare.setTimestamp(5, bill.getCheckoutDate());
        prepare.setString(6, bill.getCustomerId());
        prepare.setInt(7, StringUtil.checkValidInteger(bill.getPeopleNumber()));
        prepare.setBoolean(8, false);
        prepare.setString(9, bill.getStatus());
        prepare.setString(10, String.valueOf(bill.getCheckinDate().getMonth() + 1));
        prepare.setString(11, String.valueOf(bill.getCheckoutDate().getMonth() + 1));
        prepare.setString(12, String.valueOf(bill.getCheckinDate().getYear() + 1900));
        prepare.setString(13,  String.valueOf(bill.getCheckoutDate().getYear() + 1900));
        try {
            prepare.execute();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateBill(Map<String, String> spec, String id) {
        String query = "UPDATE `managerhotel`.`bill`";
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
    public Bill getBill(String id) throws SQLException {
        Bill billDto = new Bill();
        String query = "select * from managerhotel.bill where id = ?";
        Connection connection =  databaseSource.getDatasource();
        PreparedStatement prepare = connection.prepareStatement(query);
        prepare.setString(1, id);
        try {
            ResultSet rs = prepare.executeQuery();
            while (rs.next()){
                billDto.setId(rs.getString("id"));
                billDto.setCreatedUser(rs.getString("created_user"));
                billDto.setInvoiceDate(rs.getTimestamp("invoice_date"));
                billDto.setCheckinDate(rs.getTimestamp("checkin_date"));
                billDto.setCheckoutDate(rs.getTimestamp("checkout_date"));
                billDto.setCustomerId(rs.getString("customer_id"));
                billDto.setStatus(rs.getString("status"));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return billDto;
    }
}
