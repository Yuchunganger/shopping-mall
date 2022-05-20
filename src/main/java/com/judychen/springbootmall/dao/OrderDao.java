package com.judychen.springbootmall.dao;

import com.judychen.springbootmall.dto.OrderQueryParam;
import com.judychen.springbootmall.dto.OrderRequest;
import com.judychen.springbootmall.model.Order;

import java.util.List;

public interface OrderDao {

    Integer createOrder(OrderRequest orderedRequest);
    Order getOrderByOrderId(Integer orderId);
    List<Order> getOrders(OrderQueryParam orderQueryParam);
}
