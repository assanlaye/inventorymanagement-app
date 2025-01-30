package com.inventorysystem.dto;

import java.util.List;

public class OrderResponseDto {

    private Integer totalPages;

    private Integer pageNumber;

    private List<OrderDto> orderDtoList;

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public List<OrderDto> getOrderDtoList() {
        return orderDtoList;
    }

    public void setOrderDtoList(List<OrderDto> orderDtoList) {
        this.orderDtoList = orderDtoList;
    }
}
