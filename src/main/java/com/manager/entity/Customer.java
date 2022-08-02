package com.manager.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "customer")
//@MappedSuperclass
public class Customer
        extends EntityBase
{
//    @Id
//    @Column(name = "id", nullable = false, length = 36)
//    private String id;

//    @Column(name = "created_user")
//    private String createdUser;
    @Column(name = "name")
    private String name;
    @Column(name = "birth_day")
    private Date birthDay;
    @Column(name = "address")
    private String address;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private String phone;
    @Column(name = "type")
    private String type;


}
