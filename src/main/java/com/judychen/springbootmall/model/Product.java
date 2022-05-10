package com.judychen.springbootmall.model;

import com.judychen.springbootmall.constant.ProductCategory;
import lombok.Data;

import java.util.Date;

@Data
public class Product {

    private Integer id;
    private String teacher;
    private String title;
    private ProductCategory category;
    private String coverImageUrl;
    private String teacherImageUrl;
    private String courseUrl;
    private Integer price;
    private Integer proposalPrice;
    private Date proposalDueTime;
    private Boolean isDiscount;
    private Integer discount;
    private Integer criteriaNumSoldTickets;
    private Integer currentNumSoldTickets;
    private String status;
    private String description;
    private Date createdDate;
    private Date lastModifiedDate;

}