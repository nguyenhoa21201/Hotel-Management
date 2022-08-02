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
public class Bill
        extends EntityBase {

//    @Id
//    @Column(name = "id", length = 36)
//    private String id;
//
//    @Column(name = "created_user")
//    private String createdUser;

    @Column(name = "invoice_date")
    private Timestamp invoiceDate;
    @Column(name = "checkin_date")
    private Timestamp checkinDate;
    @Column(name = "checkout_date")
    private Timestamp checkoutDate;
    @Column(name = "customer_id")
    private String customerId;
    @Column(name = "people_number")
    private Integer peopleNumber;
    @Column(name = "status")
    private String status;

    @Column(name = "checkin_month")
    private String checkinMonth;
    @Column(name = "checkin_year")
    private String checkinYear;
    @Column(name = "checkout_month")
    private String checkoutMonth;
    @Column(name = "checkout_year")
    private String checkoutYear;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
