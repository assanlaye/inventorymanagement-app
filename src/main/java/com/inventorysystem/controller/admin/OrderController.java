package com.inventorysystem.controller.admin;

import com.inventorysystem.service.admin.Order.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("adminOrderController")
@RequestMapping("/api/admin")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @GetMapping("/orders/{pageNumber}")
    public ResponseEntity<?> getAllOrders(@PathVariable int pageNumber){
        try {
            return ResponseEntity.ok(orderService.getAllOrders(pageNumber));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong.");
        }
    }

    @GetMapping("/order/{id}/{status}")
    public ResponseEntity changeProductStatus(@PathVariable Long id,
                                              @PathVariable String status){
        boolean success = orderService.changeOrderStatus(id, status);

        if (success){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }
}
