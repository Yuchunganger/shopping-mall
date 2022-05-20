package com.judychen.springbootmall.service;

import com.judychen.springbootmall.dto.OrderDetailsRequest;
import com.judychen.springbootmall.model.OrderDetailsWithProduct;

import java.util.List;

public interface OrderDetailsService {

    void createOrderDetailsByOrderId(Integer orderId, List<OrderDetailsRequest> orderDetailsRequests);

    List<OrderDetailsWithProduct> getOrderDetailsWithProductsByOrderId(Integer orderId);
}
