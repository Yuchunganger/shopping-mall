package com.judychen.springbootmall.service.impl;

import com.judychen.springbootmall.dao.ProductDao;
import com.judychen.springbootmall.model.Product;
import com.judychen.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }
}
