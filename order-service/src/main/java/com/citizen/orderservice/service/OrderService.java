package com.citizen.orderservice.service;

import com.citizen.orderservice.dto.OrderDto;
import com.citizen.orderservice.jpa.OrderEntity;

public interface OrderService {

    OrderDto createOrder(OrderDto orderDetails);
    OrderDto getOrderByOrderId(String orderId);
    Iterable<OrderEntity> getOrdersByUserId(String userId);

}
