package com.example.ordermanagement.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderItemDto {
    @NotNull
    private Long orderId;
    @NotNull
    private Long productId;
    @NotNull
    private int quantity;
}
