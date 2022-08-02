package com.manager.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Entity
@MappedSuperclass
public class OrderDetails extends EntityBase{

//    @Id
//    @Column(name = "id", length = 36)
//    private String id;

//    @Column(name = "created_user")
//    private String createdUser;

    @Column(name = "order_id")
    private String orderId;
    @Column(name = "ref_id")
    private String refId;
    @Column(name = "ref_type")
    private String refType;
    @Column(name = "price_ref")
    private Double priceRef;
    @Column(name = "name_ref")
    private String nameRef;
    @Column(name = "unit")
    private String unit;
    @Column(name = "amount")
    private String amount;
    @Column(name = "created_month")
    private String createdMonth;
    @Column(name = "created_year")
    private String createdYear;
    @Column(name = "created_date")
    private Timestamp createdDate;

    @ManyToOne
    @JoinColumn(name = "id")
    private Order order;
}
