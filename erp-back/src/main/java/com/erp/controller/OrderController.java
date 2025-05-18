package com.erp.controller;

import com.erp.pojo.Order;
import com.erp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/listOrder")
    public Map<String, Object> listOrder(@RequestBody Order order) {
        return orderService.queryOrderListServie(order.getPageNum(),order.getPageSize(),order);
    }
}
