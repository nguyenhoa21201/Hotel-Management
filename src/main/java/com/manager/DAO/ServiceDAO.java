package com.manager.DAO;

import com.manager.entity.Room;
import com.manager.entity.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ServiceDAO {
    public void createService(Service service) throws SQLException;

    public List<Service> getAllService(Map<String, String> spec) throws SQLException;

    public void updateService(Map<String, String> spec, String id);

    public void deleteService(List<String> ids) throws SQLException;

    public Service getServiceDetail(String id) throws SQLException;
}
