package com.manager.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.File;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "service")
@MappedSuperclass
public class Service
        extends EntityBase
{
//    @Id
//    @Column(name = "id", length = 36)
//    private String id;
//
//    @Column(name = "created_user")
//    private String createdUser;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private Double price;
    @Column(name = "unit")
    private String unit;
    @Column(name = "name")
    private String name;
    @Column(name = "image")
    private String image;
    @Column(name = "amount")
    private String amount;

}
