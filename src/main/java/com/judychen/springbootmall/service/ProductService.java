package com.judychen.springbootmall.service;

import com.judychen.springbootmall.dto.ProductRequest;
import com.judychen.springbootmall.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProducts();

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProductById(Integer productId,
                           ProductRequest productRequest);

    void deleteProduct(Integer productId);
}
