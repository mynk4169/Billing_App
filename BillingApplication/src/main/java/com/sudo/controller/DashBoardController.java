package com.sudo.controller;

import com.sudo.io.DashboardResponse;
import com.sudo.io.OrderResponse;
import com.sudo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path="/dashboard")
@RequiredArgsConstructor
public class DashBoardController
{
    private final OrderService orderService;
    @GetMapping
    public DashboardResponse getDashboardData()
    {
        LocalDate today = LocalDate.now();
        Double todaySale = orderService.sumSalesByDate(today);
        Long todayOrderCount = orderService.countByOrderDate(today);
        List<OrderResponse> recentOrders = orderService.findRecentOrders();
        return  new DashboardResponse(
                todaySale!=null?todaySale:0.0,
                todayOrderCount!=null? todayOrderCount:0,
                recentOrders);
    }
}
