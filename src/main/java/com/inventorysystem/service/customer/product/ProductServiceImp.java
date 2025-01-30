package com.inventorysystem.service.customer.product;

import com.inventorysystem.dto.ProductResponseDto;
import com.inventorysystem.entity.Product;
import com.inventorysystem.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;


    public ProductServiceImp(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponseDto getAvailableProducts(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 6);
        Page<Product> productPage =
                productRepository.findByAvailable(true,pageable);

        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setPageNumber(productPage.getNumber());
        productResponseDto.setTotalPages(productPage.getTotalPages());
        productResponseDto.setProductDtoList(productPage.stream().map(Product
                ::getProductDto).collect(Collectors.toList()));

        return productResponseDto;
    }

}
