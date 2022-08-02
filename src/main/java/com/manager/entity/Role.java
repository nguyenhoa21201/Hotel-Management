package com.manager.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "role")
@MappedSuperclass
public class Role
        extends EntityBase
{

//    @Id
//    @Column(name = "id", length = 36)
//    private String id;
//
//    @Column(name = "created_user")
//    private String createdUser;

    @Column(name = "role_code")
    private String roleCode;
    @Column(name = "role_name")
    private String roleName;

}
