package com.judychen.springbootmall.dao.impl;

import com.judychen.springbootmall.dao.ProductDao;
import com.judychen.springbootmall.model.Product;
import com.judychen.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getProductById(Integer productId) {
        String sql = "SELECT product_id, product_name, category, image_url, price, stock, description," +
                        " created_date, last_modified_date " +
                        "FROM product WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        List<Product> query = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        if (query.size() > 0){
            return query.get(0);
        }else {
            return null;
        }
    }
}
