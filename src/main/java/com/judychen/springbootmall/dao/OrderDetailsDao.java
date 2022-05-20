package com.judychen.springbootmall.dao;

import com.judychen.springbootmall.dto.OrderDetailsRequest;
import com.judychen.springbootmall.model.OrderDetailsWithProduct;

import java.util.List;

public interface OrderDetailsDao {

    void createOrderDetailsByOrderId(Integer orderId , List<OrderDetailsRequest> orderDetailsRequests);
    List<OrderDetailsWithProduct> getOrderDetailsWithProductsByOrderId(Integer orderId);

}
