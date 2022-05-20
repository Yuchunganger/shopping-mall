package com.judychen.springbootmall.model;

import com.judychen.springbootmall.constant.ProductCategory;
import lombok.Data;

@Data
public class OrderDetailsWithProduct {

    private Integer orderDetailsId;
    private Integer orderId;
    private Integer productId;
    private String productName;
    private ProductCategory category;
    private String imageUrl;
    private Integer price;
    private Integer quantity;
    private String description;

}
