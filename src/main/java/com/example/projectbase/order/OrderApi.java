package com.example.projectbase.order;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/orders")
public class OrderApi {
    @Autowired
    OrderService orderService;
    @GetMapping
    public ResponseEntity<List<Order>> getList(){
        return ResponseEntity.ok(orderService.getList());
    }
    @PostMapping(path = "test01")
    public ResponseEntity<Order> test01(){
        return ResponseEntity.ok(orderService.addOrder());
    }
}
