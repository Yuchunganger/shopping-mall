package com.judychen.springbootmall.service;

import com.judychen.springbootmall.dto.ProductRequest;
import com.judychen.springbootmall.model.Product;

public interface ProductService {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProductById(Integer productId,
                           ProductRequest productRequest);
}
