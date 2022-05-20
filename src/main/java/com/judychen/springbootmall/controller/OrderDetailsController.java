package com.judychen.springbootmall.controller;

import com.judychen.springbootmall.dto.OrderDetailsRequest;
import com.judychen.springbootmall.model.OrderDetailsWithProduct;
import com.judychen.springbootmall.service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class OrderDetailsController {

    @Autowired
    OrderDetailsService orderDetailsService;

    @PostMapping("/orders/{orderId}/orderDetails")
    public ResponseEntity<?> createOrderDetails(@PathVariable Integer orderId,
                                                    @RequestBody @Valid List<OrderDetailsRequest> orderDetailsRequests){
        orderDetailsService.createOrderDetailsByOrderId(orderId, orderDetailsRequests);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/orders/{orderId}/orderDetailsWithProducts")
    public ResponseEntity<List<OrderDetailsWithProduct>> getOrderDetailsWithProductsByOrderId(@PathVariable Integer orderId){
        List<OrderDetailsWithProduct> orderDetailsWithProducts = orderDetailsService.getOrderDetailsWithProductsByOrderId(orderId);
        if (orderDetailsWithProducts == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else{
            return ResponseEntity.status(HttpStatus.OK)
                    .body(orderDetailsWithProducts);
        }
    }

}
