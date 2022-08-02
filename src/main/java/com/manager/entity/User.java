package com.manager.entity;

//import com.sun.istack.internal.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "user")
@MappedSuperclass
public class User
        extends EntityBase
{

//    @Id
//    @Column(name = "id", length = 36)
//    private String id;
//
//    @Column(name = "created_user")
//    private String createdUser;
//    @NotNull
    @Column(name = "username")
    private String username;

//    @NotNull
    @Column(name = "password")
    private String password;

    @Column(name = "role_code")
    private String roleCode;

    @Column(name = "customer_id", length = 36)
    private String customerId;

    @Column(name = "role_id", length = 36)
    private String roleId;

    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
