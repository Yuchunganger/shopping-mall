package com.judychen.springbootmall.service.impl;

import com.judychen.springbootmall.dao.OrderDao;
import com.judychen.springbootmall.dto.OrderQueryParam;
import com.judychen.springbootmall.dto.OrderRequest;
import com.judychen.springbootmall.model.Order;
import com.judychen.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;

    @Override
    public Order getOrderByOrderId(Integer orderId) {
        return orderDao.getOrderByOrderId(orderId);
    }

    @Override
    public List<Order> getOrders(OrderQueryParam orderQueryParam) {
        return orderDao.getOrders(orderQueryParam);
    }

    @Override
    public Integer createOrder(OrderRequest orderRequest) {
        return orderDao.createOrder(orderRequest);
    }
}
