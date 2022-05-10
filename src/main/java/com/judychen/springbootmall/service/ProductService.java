package com.judychen.springbootmall.service;

import com.judychen.springbootmall.dto.ProductQueryParams;
import com.judychen.springbootmall.dto.ProductRequest;
import com.judychen.springbootmall.model.Product;

import java.util.List;

public interface ProductService {

    Integer countProduct(ProductQueryParams productQueryParams);

    List<Product> getProducts(ProductQueryParams productQueryParams);

    Product getProductById(Integer id);

    Integer createProduct(ProductRequest productRequest);

    void updateProductById(Integer id,
                           ProductRequest productRequest);

    void deleteProduct(Integer id);
}
