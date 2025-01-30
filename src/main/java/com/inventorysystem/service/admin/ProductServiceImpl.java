package com.inventorysystem.service.admin;

import com.inventorysystem.dto.ProductDto;
import com.inventorysystem.dto.ProductResponseDto;
import com.inventorysystem.entity.Product;
import com.inventorysystem.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{

    private  final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public boolean postProduct(ProductDto productDto){
        try {
            Product product = new Product();

            product.setProductName(productDto.getProductName());
            product.setProductCategory(productDto.getProductCategory());
            product.setPrice(productDto.getPrice());
            product.setQuantity(productDto.getQuantity());
            product.setAvailable(true);

            productRepository.save(product);
            return true;
        } catch (Exception e){
            return false;
        }
    }
    public ProductResponseDto getAllProducts(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 6);
        Page<Product> productPage = productRepository.findAll(pageable);

        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setPageNumber(productPage.getNumber());
        productResponseDto.setTotalPages(productPage.getTotalPages());
        productResponseDto.setProductDtoList(productPage.stream().map(Product
    ::getProductDto).collect(Collectors.toList()));

        return productResponseDto;
    }
    public ProductDto getProductById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            return optionalProduct.get().getProductDto();
        } else {
            throw new EntityNotFoundException("Product not present.");
        }
    }

    public boolean updateProduct(Long id, ProductDto productDto){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()){
            Product existingProduct = optionalProduct.get();

            existingProduct.setProductName(productDto.getProductName());
            existingProduct.setProductCategory(productDto.getProductCategory());
            existingProduct.setPrice(productDto.getPrice());
            existingProduct.setQuantity(productDto.getQuantity());

            productRepository.save(existingProduct);
            return true;
        }
        return false;
    }

    public void deleteProduct(Long id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()){
            productRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Product not present");
        }
    }




}
