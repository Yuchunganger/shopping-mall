package com.judychen.springbootmall.service.impl;

import com.judychen.springbootmall.constant.ProductCategory;
import com.judychen.springbootmall.dao.ProductDao;
import com.judychen.springbootmall.dto.ProductQueryParams;
import com.judychen.springbootmall.dto.ProductRequest;
import com.judychen.springbootmall.model.Product;
import com.judychen.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;

    @Override
    public Integer countProduct(ProductQueryParams productQueryParams) {
        return productDao.countProduct(productQueryParams);
    }

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
        return productDao.getProducts(productQueryParams);
    }

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        return productDao.createProduct(productRequest);
    }

    @Override
    public void updateProductById(Integer productId,
                                  ProductRequest productRequest) {
        productDao.updateProductById(productId, productRequest);
    }

    @Override
    public void deleteProduct(Integer productId) {
        productDao.deleteProduct(productId);
    }
}
