package com.inventorysystem.service.customer.Order;

import com.inventorysystem.dto.OrderDto;
import com.inventorysystem.dto.OrderResponseDto;
import com.inventorysystem.entity.Order;
import com.inventorysystem.entity.Product;
import com.inventorysystem.entity.User;
import com.inventorysystem.enums.OrderStatus;
import com.inventorysystem.repository.OrderRepository;
import com.inventorysystem.repository.ProductRepository;
import com.inventorysystem.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("customerOrderService")
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public static final int SEARCH_RESULT_PER_PAGE = 4;

    public OrderServiceImpl(UserRepository userRepository, ProductRepository productRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public boolean postOrdering(OrderDto orderDto){

        Optional<User> optionalUser = userRepository.findById(orderDto.getUserId());
        Optional<Product> optionalProduct = productRepository.findById(orderDto.getProductId());

        if (optionalProduct.isPresent() && optionalUser.isPresent()){
            Product product = optionalProduct.get();
            if (product.getQuantity() >= orderDto.getQuantity()) { // Check stock availability
                Order order = new Order();

                order.setProduct(product);
                order.setUser(optionalUser.get());
                order.setOrderDate(orderDto.getOrderDate());

                // Calculate deliveryDate (e.g, 7 days from order date)
                order.setDeliveryDate(orderDto.getOrderDate().plusDays(7));
                order.setOrderStatus(OrderStatus.PENDING);

                // Calculate days between order and delivery date
                Long days = ChronoUnit.DAYS.between(orderDto.getOrderDate(), order.getDeliveryDate());

                order.setPrice(product.getPrice() * orderDto.getQuantity()); // Calculate total price
                order.setQuantity(orderDto.getQuantity()); // Set quantity

                // Deduct the ordered quantity from the product stock
                product.setQuantity(product.getQuantity() - orderDto.getQuantity());
                productRepository.save(product); // Save the updated product

                orderRepository.save(order);
                return true;
            } else {
                // Handle the case where there is not enough stock
                return false;
            }
        }
        return false;
    }

    public OrderResponseDto getAllOrderByUserId(Long userId, int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber, SEARCH_RESULT_PER_PAGE);

        Page<Order> orderPage = orderRepository.findAllByUserId(pageable, userId);

        OrderResponseDto orderResponseDto = new OrderResponseDto();

        orderResponseDto.setOrderDtoList(orderPage.stream().map(Order::getOrderDto).collect(Collectors.toList()));

        orderResponseDto.setPageNumber(orderPage.getPageable().getPageNumber());
        orderResponseDto.setTotalPages(orderPage.getTotalPages());

        return orderResponseDto;
    }
}