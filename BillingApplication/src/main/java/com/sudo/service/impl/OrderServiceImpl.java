package com.sudo.service.impl;

import com.sudo.entity.OrderEntity;
import com.sudo.entity.OrderItemEntity;
import com.sudo.io.*;
import com.sudo.repository.OrderEntityRepository;
import com.sudo.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService
{
    private final OrderEntityRepository orderRepo;
    @Override
    public OrderResponse createOrder(OrderRequest request)
    {
        OrderEntity newOrder = convertToOrderEntity(request);
        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setStatus(newOrder.getPaymentMethod() == PaymentMethod.CASH ?
                PaymentDetails.PaymentStatus.COMPLETED:PaymentDetails.PaymentStatus.PENDING);
        newOrder.setPaymentDetails(paymentDetails);
       List<OrderItemEntity>orderItems = request.getCartItems()
                .stream()
                .map(this::convertToOrderItemEntity)
                .collect(Collectors.toList());
       newOrder.setItems(orderItems);
        newOrder =orderRepo.save(newOrder);
        return convertToResponse(newOrder);
    }

    @Override
    public void deleteOrder(String orderId)
    {
      OrderEntity existingOrder=  orderRepo.findByOrderId(orderId).
              orElseThrow(()->new EntityNotFoundException("Order not Found"+orderId));
      orderRepo.delete(existingOrder);
    }

    @Override
    public List<OrderResponse> getLatestOrder() {
        return orderRepo.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse verifyPayment(PaymentVerificationRequest request)
    {
        OrderEntity existingOrder= orderRepo.findByOrderId(request.getOrderId())
                .orElseThrow(()->new EntityNotFoundException("Order Not Found"));
        if(!verifyRazorpaySignature(
                request.getRazorpayOrderId(),
                request.getRazorpayPaymentId(),
                request.getRazorpaySignature())){
            throw new RuntimeException("Payment verification failed");
               }
        PaymentDetails paymentDetails = existingOrder.getPaymentDetails();
        paymentDetails.setRazorpayPaymentId(request.getRazorpayPaymentId());
        paymentDetails.setRazorpayOrderId(request.getRazorpayOrderId());
        paymentDetails.setRazorpaySignature(request.getRazorpaySignature());
        paymentDetails.setStatus(PaymentDetails.PaymentStatus.COMPLETED);
        existingOrder =orderRepo.save(existingOrder);
        return convertToResponse(existingOrder);
    }

    @Override
    public Double sumSalesByDate(LocalDate date)
    {
        return orderRepo.sumSalesByDate(date);

    }

    @Override
    public Long countByOrderDate(LocalDate date) {
        return orderRepo.countByOrderDate(date);
    }


    @Override
    public List<OrderResponse> findRecentOrders() {
        return orderRepo.findRecentOrders(PageRequest.of(0,5))
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private boolean verifyRazorpaySignature(String razorpayOrderId, String razorpayPaymentId, String razorpaySignature)
    {
        return true;
    }

    private OrderEntity convertToOrderEntity(OrderRequest request)
    {
        return OrderEntity.builder()
                .customerName(request.getCustomerName())
                .phoneNumber(request.getPhoneNumber())
                .subTotal(request.getSubTotal())
                .tax(request.getTax())
                .grandTotal(request.getGrandTotal())
                .paymentMethod(PaymentMethod.valueOf(request.getPaymentMethod()))
                .build();
    }
    private OrderResponse convertToResponse(OrderEntity newOrder)
    {
        return OrderResponse.builder()
                .orderId(newOrder.getOrderId())
                .customerName(newOrder.getCustomerName())
                .phoneNumber(newOrder.getPhoneNumber())
                .subtotal(newOrder.getSubTotal())
                .grandTotal(newOrder.getGrandTotal())
                .paymentMethod(newOrder.getPaymentMethod())
                .items(newOrder.getItems().stream().map(this::convertToItemResponse)
                        .collect(Collectors.toList()))
                .paymentMethod(newOrder.getPaymentMethod())
                .tax(newOrder.getTax())
                .paymentDetails(newOrder.getPaymentDetails())
                .createdAt(newOrder.getCreatedAt())
                .build();

    }
    private OrderItemEntity convertToOrderItemEntity(OrderRequest.OrderItemRequest orderItemRequest)
    {

       return  OrderItemEntity.builder()
                .itemId(orderItemRequest.getItemId())
                .name(orderItemRequest.getName())
                .price(orderItemRequest.getPrice())
                .quantity(orderItemRequest.getQuantity())
                .build();
    }
    private OrderResponse.OrderItemResponse convertToItemResponse (OrderItemEntity orderItem)
    {
        return OrderResponse.OrderItemResponse.builder()
                .itemId(orderItem.getItemId())
                .name(orderItem.getName())
                .price(orderItem.getPrice())
                .quantity(orderItem.getQuantity())
                .build();
    }
}
