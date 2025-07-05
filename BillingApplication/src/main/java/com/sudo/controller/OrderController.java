package com.sudo.controller;

import com.sudo.io.OrderRequest;
import com.sudo.io.OrderResponse;
import com.sudo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController
{
    private final OrderService orderService;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OrderResponse createOrder(@RequestBody OrderRequest request)
    {
        System.out.println(request);
        return orderService.createOrder(request);
    }
    @DeleteMapping(path="/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("orderId")String orderId)
    {
        orderService.deleteOrder(orderId);
    }
    @GetMapping(path="/latest")
    public List<OrderResponse>getLatestOrder()
    {
        return orderService.getLatestOrder();
    }
}
