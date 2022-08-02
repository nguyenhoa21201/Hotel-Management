package com.manager.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@MappedSuperclass
public class BillDetails
        extends EntityBase
{

//    @Id
//    @Column(name = "id", length = 36)
//    private String id;
//
//    @Column(name = "created_user")
//    private String createdUser;

    @Column(name = "bill_id")
    private String billId;
    @Column(name = "ref_id")
    private String refId;
    @Column(name = "price_ref")
    private Double priceRef;
    @Column(name = "ref_type")
    private String refType;
    @Column(name = "name_ref")
    private String nameRef;
    @Column(name = "price_type")
    private String priceType;
    @Column(name = "unit")
    private String unit;
    @Column(name = "amount")
    private String amount;


}
