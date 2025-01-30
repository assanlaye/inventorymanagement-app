package com.inventorysystem.service.admin;

import com.inventorysystem.dto.ProductDto;
import com.inventorysystem.dto.ProductResponseDto;

public interface ProductService {


    boolean postProduct(ProductDto productDto);

    ProductResponseDto getAllProducts(int pageNumber);

    ProductDto getProductById(Long id);

    boolean updateProduct(Long id, ProductDto productDto);

    void deleteProduct(Long id);
}
