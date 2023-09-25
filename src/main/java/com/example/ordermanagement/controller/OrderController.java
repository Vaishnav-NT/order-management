package com.example.ordermanagement.controller;

import com.example.ordermanagement.dto.CreateOrderDto;
import com.example.ordermanagement.entity.Order;
import com.example.ordermanagement.service.OrderService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("v1/order")
@Api(tags = "Order service controller")
@RequiredArgsConstructor
public class OrderController {
    @Autowired
    private final OrderService orderService;


    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody CreateOrderDto orderDetails) throws Exception {
         orderService.createOrder(orderDetails);
         return new ResponseEntity<String>("Order created successfully", HttpStatus.CREATED);
    }

    @GetMapping
    public Page<Order> getAllOrders(@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                    @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                    @RequestParam(value = "status", required = false) String status) {
        return orderService.getAllOrders(pageNumber, pageSize, Sort.Direction.DESC, "createdAt", status);
    }

    @GetMapping("/{id}")
    public Order getAnOrder(@PathVariable("id") final Long orderId) {
        return orderService.getAnOrder(orderId);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable("id") final Long orderId) throws Exception {
        orderService.deleteOrder(orderId);
        return new ResponseEntity<String>("Order with id " + orderId + " deleted successfully", HttpStatus.CREATED);
    }
}
