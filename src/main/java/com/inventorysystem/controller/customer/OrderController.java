package com.inventorysystem.controller.customer;

import com.inventorysystem.dto.OrderDto;
import com.inventorysystem.service.customer.Order.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("customerOrderController")
@RequestMapping("/api/customer")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order")
    public ResponseEntity<?> postOrdering(@RequestBody OrderDto orderDto){
        boolean success = orderService.postOrdering(orderDto);

        if (success){
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @GetMapping("/orders/{userId}/pageNumber")
    public ResponseEntity<?> getAllOrdersByUserId(@PathVariable Long userId,
                                                  @PathVariable int pageNumber){
        try {
            return ResponseEntity.ok(orderService.getAllOrderByUserId(userId,
                    pageNumber));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }


}
