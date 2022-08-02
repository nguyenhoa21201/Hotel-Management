package com.manager.service;

import com.manager.dto.BusinessReport;
import com.manager.dto.BusinessReportRequest;

import java.sql.SQLException;
import java.util.List;

public interface AdminService {
    List<BusinessReport> getBusinessReport(BusinessReportRequest request) throws SQLException;
}
