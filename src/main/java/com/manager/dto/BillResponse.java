package com.manager.dto;


import com.manager.entity.BillDetails;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BillResponse {

    private String id;
    private String createdUser;
    private Double totalPrice;
    private Timestamp invoiceDate;
    private Timestamp checkinDate;
    private Timestamp checkoutDate;
    private String customerId;
    private String customerName;
    private String status;
    private List<BillDetails> details;

}
