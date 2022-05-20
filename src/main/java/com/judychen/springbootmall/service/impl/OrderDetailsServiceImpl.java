package com.judychen.springbootmall.service.impl;

import com.judychen.springbootmall.dao.OrderDetailsDao;
import com.judychen.springbootmall.dto.OrderDetailsRequest;
import com.judychen.springbootmall.model.OrderDetailsWithProduct;
import com.judychen.springbootmall.service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderDetailsServiceImpl implements OrderDetailsService {

    @Autowired
    OrderDetailsDao orderDetailsDao;

    @Override
    public void createOrderDetailsByOrderId(Integer orderId, List<OrderDetailsRequest> orderDetailsRequests) {
        orderDetailsDao.createOrderDetailsByOrderId(orderId, orderDetailsRequests);
    }

    @Override
    public List<OrderDetailsWithProduct> getOrderDetailsWithProductsByOrderId(Integer orderId) {
        return orderDetailsDao.getOrderDetailsWithProductsByOrderId(orderId);
    }
}
