package com.example.ordermanagement.service;

import com.example.ordermanagement.dto.CreateOrderItemDto;
import com.example.ordermanagement.entity.Order;
import com.example.ordermanagement.entity.OrderItem;
import com.example.ordermanagement.entity.Product;
import com.example.ordermanagement.repository.OrderItemRepository;
import com.example.ordermanagement.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductService productService;

    public void createOrderItem(CreateOrderItemDto orderItemDetails) throws Exception {
        Product product = productService.getProductById(orderItemDetails.getProductId());
        Order order = orderRepository.getReferenceById(orderItemDetails.getOrderId());
        OrderItem orderItem = OrderItem.builder()
                .order(order)
                .product(product)
                .quantity(orderItemDetails.getQuantity())
                .price(product.getUnitPrice())
                .rowTotal(orderItemDetails.getQuantity() * product.getUnitPrice())
                .build();
        orderItemRepository.save(orderItem);
    }

    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    public OrderItem getAnOrderItem(Long orderId) throws Exception {
        Optional<OrderItem> optionalOrderItem = orderItemRepository.findById(orderId);
        if (optionalOrderItem.isPresent()) {
            return optionalOrderItem.get();
        } else {
            throw new Exception("No order item found with given id");
        }
    }
}
