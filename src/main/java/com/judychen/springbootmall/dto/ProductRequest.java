package com.judychen.springbootmall.dto;

import com.judychen.springbootmall.constant.ProductCategory;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class ProductRequest {

    @NotNull
    private String teacher;
    @NotNull
    private String title;
    @NotNull
    private ProductCategory category;
    @NotNull
    private String coverImageUrl;
    @NotNull
    private String teacherImageUrl;
    @NotNull
    private String courseUrl;
    @NotNull
    private Integer price;
    private Integer proposalPrice;
    @NotNull
    private Date proposalDueTime;
    private Boolean isDiscount;
    private Integer discount;
    @NotNull
    private Integer criteriaNumSoldTickets;
    @NotNull
    private Integer currentNumSoldTickets;
    private String status;
    private String description;

}
