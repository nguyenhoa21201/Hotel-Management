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
public class Order extends EntityBase{

//    @Id
//    @Column(name = "id", length = 36)
//    private String id;

//    @Column(name = "created_user")
//    private String createdUser;

    @Column(name = "customer_id")
    private String customerId;
    @Column(name = "status")
    private String status;
    @Column(name = "order_type")
    private String orderType;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
