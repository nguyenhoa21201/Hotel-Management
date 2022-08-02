package com.manager.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public class UserCustomer {
    private String userId;
    private String username;
    private String name;
    private Date birthDay;
    private String address;
    private String email;
    private String phone;
    private String roleCode;
}
