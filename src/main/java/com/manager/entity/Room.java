package com.manager.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "room")
@MappedSuperclass
public class Room
        extends EntityBase
{

//    @Id
//    @Column(name = "id", length = 36)
//    private String id;
//
//    @Column(name = "created_user")
//    private String createdUser;

    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "square")
    private String square;
    @Column(name = "bed_number")
    private String bedNumber;
    @Column(name = "people_number")
    private String peopleNumber;
    @Column(name = "price")
    private String price;
    @Column(name = "discount_price")
    private String discountPrice;
    @Column(name = "status")
    private String status;
    @Column(name = "image")
    private  String image;


}
