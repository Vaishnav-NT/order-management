package com.example.ordermanagement.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductsDetail {
    @NotNull
    private long productId;
    @NotNull
    private int quantity;
}
