package com.judychen.springbootmall.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OrderDetailsRequest {

    @NotNull
    private Integer productId;
    @NotNull
    private Integer quantity;
}
