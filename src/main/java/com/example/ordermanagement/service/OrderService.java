package com.example.ordermanagement.service;

import com.example.ordermanagement.dto.CreateOrderDto;
import com.example.ordermanagement.dto.CreateOrderItemDto;
import com.example.ordermanagement.dto.OrderProductsDetail;
import com.example.ordermanagement.entity.Customer;
import com.example.ordermanagement.entity.Order;
import com.example.ordermanagement.entity.Product;
import com.example.ordermanagement.exception.ObjectNotFoundException;
import com.example.ordermanagement.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final CustomerService customerService;
    @Autowired
    private final ProductService productService;
    @Autowired
    private final OrderItemService orderItemService;

    public void createOrder(CreateOrderDto orderDetails) throws Exception {
        Customer customer = customerService.getCustomerById(orderDetails.getCustomerId());

        int totalQuantity = 0;
        int totalPrice = 0;

        for (OrderProductsDetail orderProductsDetail : orderDetails.getOrderProductsDetailList()) {
            Product product = productService.getProductById(orderProductsDetail.getProductId());
            totalQuantity += orderProductsDetail.getQuantity();
            totalPrice += orderProductsDetail.getQuantity() * product.getUnitPrice();
        }


        Order order = Order.builder()
                .customer(customer)
                .status("ORDER_PLACED")
                .totalPrice(totalPrice)
                .totalQuantity(totalQuantity)
                .build();

        Order savedOrder = orderRepository.save(order);

        log.info("Created new order successfully");

        for (OrderProductsDetail orderProductsDetail: orderDetails.getOrderProductsDetailList()) {
            CreateOrderItemDto orderItemDetails =  new CreateOrderItemDto(savedOrder.getId(), orderProductsDetail.getProductId(), orderProductsDetail.getQuantity());
            orderItemService.createOrderItem(orderItemDetails);
        }
    }

    public Page<Order> getAllOrders(int pageNumber, int pageSize, Sort.Direction direction, String sortBy, String filterParameter) {
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Specification<Order> specification = Specification.where(null);
        if (filterParameter != null) {
            specification = specification.and((root, query, builder) -> builder.equal(root.get("status"), filterParameter));
        }
        return orderRepository.findAll(specification, pageable);
    }

    public Order getAnOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new ObjectNotFoundException(Order.class));
//        Optional<Order> orderOptional = orderRepository.findById(orderId);
//        if (orderOptional.isPresent()) {
//            return orderOptional.get();
//        } else {
//            log.error("\nNo order found with id" + orderId + "\n");
//            throw new CustomException(HttpStatus.NOT_FOUND, "No order found with given id");
//        }
    }

    public void deleteOrder(Long orderId) throws Exception {
        orderRepository.deleteById(orderId);
    }
}
