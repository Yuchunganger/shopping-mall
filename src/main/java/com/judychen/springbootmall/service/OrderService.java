package com.judychen.springbootmall.service;

import com.judychen.springbootmall.dto.OrderQueryParam;
import com.judychen.springbootmall.dto.OrderRequest;
import com.judychen.springbootmall.model.Order;

import java.util.List;

public interface OrderService {

    Integer createOrder(OrderRequest orderRequest);
    Order getOrderByOrderId(Integer orderId);
    List<Order> getOrders(OrderQueryParam orderQueryParam);

}
