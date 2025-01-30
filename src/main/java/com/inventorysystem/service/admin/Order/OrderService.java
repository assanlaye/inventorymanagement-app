package com.inventorysystem.service.admin.Order;

import com.inventorysystem.dto.OrderResponseDto;

public interface OrderService {
    OrderResponseDto getAllOrders(int pageNumber);

    boolean changeOrderStatus(Long id, String status);
}
