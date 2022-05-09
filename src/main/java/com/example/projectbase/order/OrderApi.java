package com.example.projectbase.order;

import com.example.projectbase.search.OrderSpecification;
import com.example.projectbase.search.SearchCriteria;
import com.example.projectbase.search.SearchCriteriaOperator;
import com.example.projectbase.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping(path = "api/v1/orders")
public class OrderApi {
    @Autowired
    OrderService orderService;

    @GetMapping
    public ResponseEntity<?> getList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "") String productName,
            @RequestParam(defaultValue = "") String userName,
            @RequestParam(defaultValue = "") String dateFrom,
            @RequestParam(defaultValue = "") String dateTo,
            @RequestParam(required = false, defaultValue = "") String userId,
            @RequestParam(defaultValue = "0", required = false) int status,
            @RequestParam(defaultValue = "false", required = false) boolean desc

    ) {

        return ResponseEntity.ok(orderService.findAll(page, limit,productName,userName,dateFrom,dateTo,userId,status,desc));
    }

}
