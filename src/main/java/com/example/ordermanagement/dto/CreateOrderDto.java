package com.example.ordermanagement.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOrderDto {
    @NotNull
    private Long customerId;
    @NotNull
    private List<OrderProductsDetail> orderProductsDetailList;
}
