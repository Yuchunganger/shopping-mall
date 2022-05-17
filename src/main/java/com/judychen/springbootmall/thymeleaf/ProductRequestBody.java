package com.judychen.springbootmall.thymeleaf;

import lombok.Data;

@Data
public class ProductRequestBody {
    private Integer productId;
    private String productName;
    private String category;
    private String imageUrl;
    private Integer price;
    private Integer stock;
    private String description;
}
