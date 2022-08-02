package com.manager.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
public class SearchRoomRequest {
    private String name;
    private String square;
    private String description;
    private String bedNumber;
    private String peopleNumber;
    private String price;
    private String discountPrice;
    private String status;
}
