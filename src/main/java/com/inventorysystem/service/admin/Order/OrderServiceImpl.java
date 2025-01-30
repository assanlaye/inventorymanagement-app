package com.inventorysystem.service.admin.Order;

import com.inventorysystem.dto.OrderResponseDto;
import com.inventorysystem.entity.Order;
import com.inventorysystem.entity.Product;
import com.inventorysystem.enums.OrderStatus;
import com.inventorysystem.repository.OrderRepository;
import com.inventorysystem.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("adminOrderService")
public class OrderServiceImpl implements  OrderService{

    private final ProductRepository productRepository;

    private final OrderRepository orderRepository;

    public OrderServiceImpl(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }


    public static final int SEARCH_RESULT_PER_PAGE = 4;

    public OrderResponseDto getAllOrders(int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber, SEARCH_RESULT_PER_PAGE);

        Page<Order> orderPage = orderRepository.findAll(pageable);

        OrderResponseDto orderResponseDto = new OrderResponseDto();

        orderResponseDto.setOrderDtoList(orderPage.stream().map(Order::getOrderDto).collect(Collectors.toList()));

        orderResponseDto.setPageNumber(orderPage.getPageable().getPageNumber());
        orderResponseDto.setTotalPages(orderPage.getTotalPages());

        return orderResponseDto;


    }

    public boolean changeOrderStatus(Long id, String status){
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()){
            Order existingOrder = optionalOrder.get();

            if (Objects.equals(status, "Approved")){
                existingOrder.setOrderStatus(OrderStatus.APPROVED);
            } else {
                existingOrder.setOrderStatus(OrderStatus.REJECTED);
            }

            orderRepository.save(existingOrder);

            Product existingProduct = existingOrder.getProduct();
            existingProduct.setAvailable(false);

            productRepository.save(existingProduct);

            return true;

        }
        return false;
    }
}
