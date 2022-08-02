package com.manager.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BusinessReport {
    private Long total;
    private String createdMonth;
    private String createdYear;
}
