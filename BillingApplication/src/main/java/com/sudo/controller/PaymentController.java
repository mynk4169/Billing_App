package com.sudo.controller;

import com.razorpay.RazorpayException;
import com.sudo.io.OrderResponse;
import com.sudo.io.PaymentRequest;
import com.sudo.io.PaymentVerificationRequest;
import com.sudo.io.RazorpayOrderResponse;
import com.sudo.service.OrderService;
import com.sudo.service.RazorpayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController
{
    private final RazorpayService razorpayService;
    private final OrderService orderService;

    @PostMapping(path="/create-order")
    @ResponseStatus(HttpStatus.CREATED)
    public RazorpayOrderResponse createRazorpayOrder(@RequestBody PaymentRequest paymentRequest)throws RazorpayException
    {
        return  razorpayService.createOrder(paymentRequest.getAmount(),paymentRequest.getCurrency());
    }
    @PostMapping("/verify")
    public OrderResponse verifyPayment(@RequestBody PaymentVerificationRequest request)
    {
        return orderService.verifyPayment(request);
    }
}
