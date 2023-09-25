package com.example.ordermanagement.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateOrderDto {
    private String status;
    private int totalQuantity;
    private int totalPrice;
}
