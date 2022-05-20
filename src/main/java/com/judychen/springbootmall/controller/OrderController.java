package com.judychen.springbootmall.controller;

import com.judychen.springbootmall.dto.OrderQueryParam;
import com.judychen.springbootmall.dto.OrderRequest;
import com.judychen.springbootmall.model.Order;
import com.judychen.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<Order> getOrderByOrderId(@PathVariable @NotNull Integer orderId){
        Order order = orderService.getOrderByOrderId(orderId);
        if (order == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else{
            return ResponseEntity.status(HttpStatus.OK)
                    .body(order);
        }
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrders(@RequestParam(required = false) Integer memberId){
        OrderQueryParam orderQueryParam = new OrderQueryParam();
        orderQueryParam.setMemberId(memberId);
        List<Order> orders = orderService.getOrders(orderQueryParam);
        return ResponseEntity.status(HttpStatus.OK)
                .body(orders);
    }



    @PostMapping("/order")
    public ResponseEntity<Integer> createOrder(@Valid @RequestBody OrderRequest orderRequest){
        Integer orderId = orderService.createOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderId);
    }

}
