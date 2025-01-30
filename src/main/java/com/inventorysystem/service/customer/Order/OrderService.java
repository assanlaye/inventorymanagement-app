package com.inventorysystem.service.customer.Order;

import com.inventorysystem.dto.OrderDto;
import com.inventorysystem.dto.OrderResponseDto;

public interface OrderService {
    boolean postOrdering(OrderDto orderDto);

    OrderResponseDto getAllOrderByUserId(Long userId, int pageNumber);
}
