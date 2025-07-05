package com.sudo.service;


import com.sudo.io.OrderRequest;
import com.sudo.io.OrderResponse;
import com.sudo.io.PaymentVerificationRequest;

import java.time.LocalDate;
import java.util.List;

public interface OrderService
{
    OrderResponse createOrder(OrderRequest request);
    void deleteOrder(String id);
    List<OrderResponse> getLatestOrder();
    OrderResponse verifyPayment(PaymentVerificationRequest request);
    Double sumSalesByDate (LocalDate date);
    Long countByOrderDate(LocalDate date);
    List<OrderResponse> findRecentOrders();
}
