package com.manager.DAOImpl;

import com.manager.DAO.ServiceDAO;
import com.manager.config.DatabaseSource;
import com.manager.entity.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServiceDAOImpl implements ServiceDAO {

    public static DatabaseSource databaseSource = new DatabaseSource();

    @Override
    public void createService(Service service) throws SQLException {
        String query = "INSERT INTO `managerhotel`.`service` (`id`, `created_user`, `description`, `price`, " +
                "`unit`, `name`, `amount`, `is_deleted`, `image`)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";
        Connection connection = databaseSource.getDatasource();
        PreparedStatement prepare = connection.prepareStatement(query);
        try {
            prepare.setString(1, service.getId());
            prepare.setString(2, "SYSTEM");
            prepare.setString(3, service.getDescription());
            prepare.setDouble(4, service.getPrice());
            prepare.setString(5, service.getUnit());
            prepare.setString(6, service.getName());
            prepare.setString(7, service.getAmount());
            prepare.setBoolean(8, false);
            prepare.setString(9, service.getImage());
            prepare.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Service> getAllService(Map<String, String> spec) throws SQLException {
        List<Service> services = new ArrayList<>();
        String query = "select * from service ";
        List<String> predicates = new ArrayList<>();
        if (!spec.isEmpty()) {
            query = query + "where ";
            for (Map.Entry<String, String> entry : spec.entrySet()) {
                predicates.add(" " + entry.getKey() + " LIKE " + entry.getValue());
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
                Service service = new Service();

                service.setId(rs.getString("id"));
                service.setName(rs.getString("name"));
                service.setDescription(rs.getString("description"));
                service.setPrice(rs.getDouble("price"));
                service.setUnit(rs.getString("unit"));
                service.setImage(rs.getString("image"));
                service.setAmount(rs.getString("amount"));
                services.add(service);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
    }

    @Override
    public void updateService(Map<String, String> spec, String id) {
        String query = "UPDATE `managerhotel`.`service`";
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
    public void deleteService(List<String> ids) throws SQLException {
//        String query = "update room set is_deleted = ? where id in ?";
//        Connection connection = databaseSource.getDatasource();
//        PreparedStatement prepare = connection.prepareStatement(query);
//        try {
//            prepare.setBoolean(1, false);
//            ResultSet rs = prepare.executeQuery();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public Service getServiceDetail(String id) throws SQLException {
        String query = "select * from service where id = ?";
        Service service = new Service();
        Connection connection = databaseSource.getDatasource();
        PreparedStatement prepare = connection.prepareStatement(query);
        try {
            prepare.setString(1, id);
            ResultSet rs = prepare.executeQuery();

            while (rs.next()) {
                service.setId(rs.getString("id"));
                service.setName(rs.getString("name"));
                service.setDescription(rs.getString("description"));
                service.setPrice(rs.getDouble("price"));
                service.setImage(rs.getString("image"));
                service.setAmount(rs.getString("amount"));
                service.setAmount(rs.getString("unit"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return service;
    }
}
