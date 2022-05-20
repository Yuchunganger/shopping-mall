package com.judychen.springbootmall.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class Order {

    @NotNull
    private Integer orderId;
    @NotNull
    private Integer memberId;
    @NotNull
    private Date createdDate;
    @NotNull
    private Date lastModifiedDate;

}
