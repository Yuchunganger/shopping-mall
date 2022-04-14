package com.judychen.springbootmall.dao;

import com.judychen.springbootmall.constant.ProductCategory;
import com.judychen.springbootmall.dto.ProductRequest;
import com.judychen.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getProducts(ProductCategory category,
                              String search);

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProductById(Integer productId,
                              ProductRequest productRequest);

    void deleteProduct(Integer productId);
}
