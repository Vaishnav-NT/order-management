package com.example.ordermanagement.service;

import com.example.ordermanagement.dto.CreateOrderDto;
import com.example.ordermanagement.dto.OrderProductsDetail;
import com.example.ordermanagement.entity.Customer;
import com.example.ordermanagement.entity.Order;
import com.example.ordermanagement.entity.Product;
import com.example.ordermanagement.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private OrderService orderService;
    @Mock
    private  CustomerService customerService;
    @Mock
    private  ProductService productService;
    @Mock
    private  OrderItemService orderItemService;


    @Test
    @DisplayName("Test get all order with filter")
    public void testOnGetAllOrderWithFilter() throws Exception {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(0, 5, sort);
        Specification<Order> specification = Specification.where(null);
        specification.and((root, query, builder) -> builder.equal(root.get("status"), "IN_TRANSIT"));
        List<Order> sampleOrders = Arrays.asList(Order.builder().id(1L).status("IN_TRANSIT").build(),
                Order.builder().id(2L).status("IN_TRANSIT").build());
        Page<Order> pages = new PageImpl<>(sampleOrders);
        when(orderRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(pages);

        Page<Order> orders = orderService.getAllOrders(0, 5, Sort.Direction.DESC, "id", "IN_TRANSIT");
        assertEquals(pages,orders);
        verify(orderRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    @DisplayName("Test get all order without filter")
    public void testOnGetAllOrderWithoutFilter() throws Exception {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(0, 5, sort);
        Specification<Order> specification = Specification.where(null);
        List<Order> sampleOrders = Arrays.asList(Order.builder().id(1L).status("IN_TRANSIT").build(),
                Order.builder().id(2L).status("DELIVERED").build());
        Page<Order> pages = new PageImpl<>(sampleOrders);
        when(orderRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(pages);

        Page<Order> orders = orderService.getAllOrders(0, 5, Sort.Direction.DESC, "id", null);
        assertEquals(pages,orders);
        verify(orderRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    @DisplayName("Test get an order with a valid id")
    public void testSuccessOnGetAnOrder() throws Exception {
        Long id = 1L;
        when(orderRepository.findById(id)).thenReturn(Optional.of(Order.builder().id(id).build()));

        Order order = orderService.getAnOrder(id);
        assertEquals(id, order.getId());
        verify(orderRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Test get an order with a invalid id")
    public void testFailureOnGetAnOrder() {
        Long id = 1L;
        assertThrows(Exception.class, () -> orderService.getAnOrder(id));
        verify(orderRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Test to create new order")
    public void testOnCreateOrder() throws Exception {
        Customer customer = Customer.builder().id(1L).name("Customer").build();
        Product product = Product.builder().id(1L).name("Product").unitPrice(10).build();

        when(customerService.getCustomerById(1L)).thenReturn(customer);
        when(productService.getProductById(1L)).thenReturn(product);

        List<OrderProductsDetail> orderProductsDetailList = List.of(new OrderProductsDetail(1L, 1));
        CreateOrderDto createOrderDto = new CreateOrderDto(1L, orderProductsDetailList);

        Order order = Order.builder()
                .id(1L)
                .customer(customer)
                .status("ORDER_PLACED")
                .totalPrice(10)
                .totalQuantity(1)
                .build();

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        orderService.createOrder(createOrderDto);
    }

    @Test
    @DisplayName("Valid order deletion")
    public void testValidOrderDeletion() throws Exception {
        Long id = 1L;

        orderService.deleteOrder(id);

        verify(orderRepository, times(1)).deleteById(id);
    }

//    @Test
//    @DisplayName("Invalid order deletion")
//    public void testInvalidOrderDeletion() throws Exception {
//        Long id = 1L;
//
//        assertThrows(IllegalArgumentException.class, () -> orderService.deleteOrder(id));
//
//        verify(orderRepository, times(1)).deleteById(id);
//    }

}