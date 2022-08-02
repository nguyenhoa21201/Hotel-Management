package com.manager.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SearchCustomerRequest {
    private String name;
    private String email;
    private String phone;
}
