package com.judychen.springbootmall.dao.impl;

import com.judychen.springbootmall.constant.ProductCategory;
import com.judychen.springbootmall.dao.ProductDao;
import com.judychen.springbootmall.dto.ProductRequest;
import com.judychen.springbootmall.model.Product;
import com.judychen.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String sql = "INSERT INTO product (product_name, category, image_url, price, stock, description, created_date, last_modified_date) VALUES (:productName, :category, :imageUrl,  :price, :stock, :description, :createdDate, :lastModifiedDate)";
        Map<String, Object> map = new HashMap<>();
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        Integer productId = keyHolder.getKey().intValue();

        return productId;
    }

    @Override
    public void updateProductById(Integer productId,
                                     ProductRequest productRequest) {
        String sql = "UPDATE product " +
                        "SET product_name = :productName, category = :category, image_url = :imageUrl, price = :price, stock = :stock, description = :description, last_modified_date = :lastModifiedDate " +
                        "WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        map.put("lastModifiedDate", new Date());

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public List<Product> getProducts(ProductCategory category,
                                     String search) {
        String sql = "SELECT product_id, product_name, category, image_url, price, stock, description," +
                        " created_date, last_modified_date FROM product WHERE 1=1";
        Map<String, Object> map = new HashMap<>();

        if(category != null){
            sql = sql + " AND category = :category";
            map.put("category", category.name());
        }

        if(search != null){
            sql = sql + " AND product_name LIKE :search";
            map.put("search", '%' + search  + '%');
        }

        List<Product> products = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        return products;
    }

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

    @Override
    public void deleteProduct(Integer productId) {
        String sql = "DELETE FROM product WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        namedParameterJdbcTemplate.update(sql, map);
    }
}