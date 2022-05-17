package com.judychen.springbootmall.thymeleaf;

import lombok.Data;

import java.util.List;

@Data
public class Product {

    private Integer productId;
    private String productName;
    private String category;
    private String imageUrl;
    private Integer price;
    private Integer stock;
    private String description;
    private String createdDate;
    private String lastModifiedDate;

}
