package com.manager.dto;

import com.manager.entity.OrderDetails;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DetailOrder {
    private String id;
    private String orderName;
    private String customerName;
    private String customerPhone;
    private String orderType;
    private String status;
    private List<OrderDetails> orderDetails;
}
