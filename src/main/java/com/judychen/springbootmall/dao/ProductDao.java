package com.judychen.springbootmall.dao;

import com.judychen.springbootmall.model.Product;

public interface ProductDao {

    Product getProductById(Integer productId);
}
