package com.judychen.springbootmall.model;

import lombok.Data;

@Data
public class OrderDetails {

    private Integer orderDetailsId;
    private Integer orderId;
    private Integer productId;
    private Integer quantity;

}
