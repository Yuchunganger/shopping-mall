package com.judychen.springbootmall.dao.impl;

import com.judychen.springbootmall.dao.OrderDetailsDao;
import com.judychen.springbootmall.dto.OrderDetailsRequest;
import com.judychen.springbootmall.model.OrderDetailsWithProduct;
import com.judychen.springbootmall.rowmapper.OrderDetailsWithProductsRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderDetailsDaoImpl implements OrderDetailsDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void createOrderDetailsByOrderId(Integer orderId, List<OrderDetailsRequest> orderDetailsRequests) {
        String sql = "INSERT INTO orderDetails (order_id, product_id, quantity) VALUES (:orderId, :productId, :quantity)";
        Map<String, Object> map = new HashMap<>();
        for (OrderDetailsRequest orderDetailsRequest: orderDetailsRequests) {
            map.put("orderId", orderId);
            map.put("productId", orderDetailsRequest.getProductId());
            map.put("quantity", orderDetailsRequest.getQuantity());
            namedParameterJdbcTemplate.update(sql, map);
        }
    }

    @Override
    public List<OrderDetailsWithProduct> getOrderDetailsWithProductsByOrderId(Integer orderId) {
        String sql = "SELECT order_details_id, order_id, orderDetails.product_id, product_name, category, image_url, price, quantity, description " +
                "FROM orderDetails, product WHERE order_id = :orderId AND orderDetails.product_id = product.product_id";
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);

        List<OrderDetailsWithProduct> orderDetailsWithProducts = namedParameterJdbcTemplate.query(sql, map, new OrderDetailsWithProductsRowMapper());

        if (orderDetailsWithProducts.size() > 0){
            return orderDetailsWithProducts;
        }else {
            return null;
        }
    }
}
