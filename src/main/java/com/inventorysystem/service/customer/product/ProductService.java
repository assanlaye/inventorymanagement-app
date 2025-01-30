package com.inventorysystem.service.customer.product;

import com.inventorysystem.dto.ProductResponseDto;

public interface ProductService {
    ProductResponseDto getAvailableProducts(int pageNumber);
}
