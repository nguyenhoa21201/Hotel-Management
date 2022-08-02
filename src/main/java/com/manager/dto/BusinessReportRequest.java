package com.manager.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
@NoArgsConstructor
public class BusinessReportRequest {
    private String month;
    private String year;

    private Timestamp fromDate;
    private Timestamp toDate;

}
