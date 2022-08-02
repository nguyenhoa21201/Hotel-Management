package com.manager.serviceImpl;


import com.manager.DAO.OrderDetailDao;
import com.manager.DAOImpl.OrderDetailDaoImpl;
import com.manager.dto.BusinessReport;
import com.manager.dto.BusinessReportRequest;
import com.manager.service.AdminService;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.sql.SQLException;
import java.util.List;

public class AdminServiceImpl implements AdminService {
    @Override
    public List<BusinessReport> getBusinessReport(BusinessReportRequest request) throws SQLException {
        OrderDetailDaoImpl dao = new OrderDetailDaoImpl();
        return dao.getBusinessReport(request);
    }
}
