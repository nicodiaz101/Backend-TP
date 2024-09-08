package com.uade.tpo.demo.controllers.orders;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.demo.controllers.movies.MovieRequest;
import com.uade.tpo.demo.entity.Order;
import com.uade.tpo.demo.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
    
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Object> createOrder(@RequestBody OrderRequest orderRequest) {
        try {
            Order result = orderService.createOrder(orderRequest.getUser().getEmail() , 
                                                    orderRequest.getMovies().stream()
                                                                .map(MovieRequest::getTitle)
                                                                .toList());
            return ResponseEntity.created(URI.create("/orders/" + result.getOrder_id())).body(result);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Bad Request: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<Page<Order>> getOrders(@RequestParam(required = false) Integer page,
    @RequestParam(required = false) Integer size) {
        if (page == null || size == null)
            return ResponseEntity.ok(orderService.getOrders(PageRequest.of(0, Integer.MAX_VALUE)));
        return ResponseEntity.ok(orderService.getOrders(PageRequest.of(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        Optional<Order> order = orderService.getOrderById(orderId);
        if (order.isPresent())
            return ResponseEntity.ok(order.get());

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable Long userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        if (!orders.isEmpty())
            return ResponseEntity.ok(orders);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/date")
    public ResponseEntity<List<Order>> getOrdersByOrderDate(@RequestParam String orderDate) {
        List<Order> orders = orderService.getOrderByOrderDate(orderDate);
        if (!orders.isEmpty())
            return ResponseEntity.ok(orders);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateOrder(@PathVariable Long id, @RequestBody Order orderDetails) {
        try{
            Order updatedOrder = orderService.updateOrder(id, orderDetails);
            if (updatedOrder != null) {
                return ResponseEntity.ok().location(URI.create("/orders/" + updatedOrder.getOrder_id())).body(updatedOrder);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }   
        }
        catch (RuntimeException e) {
            return new ResponseEntity<>("Bad Request: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteOrderById(@PathVariable Long id) {
        try {
            boolean isDeleted = orderService.deleteOrderById(id);
            if (isDeleted) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllOrders() {
        try {
            orderService.deleteAllOrders();
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}