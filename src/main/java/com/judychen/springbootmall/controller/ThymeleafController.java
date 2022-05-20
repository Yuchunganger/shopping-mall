package com.judychen.springbootmall.controller;

import com.judychen.springbootmall.constant.ProductCategory;
import com.judychen.springbootmall.dto.ProductRequest;
import com.judychen.springbootmall.thymeleaf.Product;
import com.judychen.springbootmall.thymeleaf.ProductRequestBody;
import com.judychen.springbootmall.util.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Controller
public class ThymeleafController {

    @GetMapping("/shop")
    public String index() {
        return "index";
    }

    @GetMapping("/shop/products")
    public String getProducts() {
        return "getProducts";
    }

    @GetMapping("/shop/products/create")
    public String createProduct() {
        return "createProduct";
    }

    @GetMapping("/shop/orders")
    public String getOrder() {
        return "getOrders";
    }

    @GetMapping("/shop/products/update")
    public String updateProduct(){
        return "updateProduct";
    }
}





