package com.manager.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateRoomRequest {
    private String name;
    private String square;
    private String description;
    private String bedNumber;
    private String peopleNumber;
    private String price;
    private String discountPrice;
    private String status;
}
