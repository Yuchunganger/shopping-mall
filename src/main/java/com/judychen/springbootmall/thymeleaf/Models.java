package com.judychen.springbootmall.thymeleaf;

import com.judychen.springbootmall.util.Page;
import lombok.Data;

import java.util.List;

@Data
public class Models {

    private String searched;
    private Integer limit;
    private Page<Product> products;
    private List<Integer> pageIndexList;


}
