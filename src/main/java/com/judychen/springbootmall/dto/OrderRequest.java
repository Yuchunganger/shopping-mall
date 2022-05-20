package com.judychen.springbootmall.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OrderRequest {

    @NotNull
    private Integer memberId;

}
