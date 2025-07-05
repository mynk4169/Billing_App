package com.sudo.service;

import com.razorpay.RazorpayException;
import com.sudo.io.RazorpayOrderResponse;

public interface RazorpayService
{
    RazorpayOrderResponse createOrder(Double amount,String currency) throws RazorpayException;
}
