package com.judychen.springbootmall.dao.impl;

import com.judychen.springbootmall.dao.OrderDao;
import com.judychen.springbootmall.dto.OrderQueryParam;
import com.judychen.springbootmall.dto.OrderRequest;
import com.judychen.springbootmall.model.Order;
import com.judychen.springbootmall.rowmapper.OrderRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderDaoImpl implements OrderDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Order getOrderByOrderId(Integer orderId) {
        String sql = "SELECT order_id, member_id, created_date, last_modified_date FROM orders WHERE order_id = :orderId";
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        List<Order> orders = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());
        if ( orders.size() > 0 ){
            Order order = orders.get(0);
            return order;
        }else{
            return null;
        }
    }

    @Override
    public List<Order> getOrders(OrderQueryParam orderQueryParam) {
        String sql = "SELECT order_id, member_id, created_date, last_modified_date FROM orders WHERE 1=1";
        Map<String, Object> map = new HashMap<>();

        if (orderQueryParam.getMemberId() != null){
            sql += " AND member_id = :memberId";
            map.put("memberId", orderQueryParam.getMemberId());
        }

        List<Order> orders = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());
        if (orders.size() > 0){
            return orders;
        }else{
            return null;
        }
    }

    @Override
    public Integer createOrder(OrderRequest orderedRequest) {
        String sql = "INSERT INTO orders (member_id, created_date, last_modified_date) VALUES (:memberId, :createdDate, :lastModifiedDate)";
        Map<String, Object> map = new HashMap<>();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        map.put("memberId", orderedRequest.getMemberId());
        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
        if (keyHolder.getKey() != null) {
            Integer orderId = keyHolder.getKey().intValue();
            return orderId;
        }else{
            return null;
        }
    }
}
