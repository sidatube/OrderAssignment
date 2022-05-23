package com.example.projectbase.product;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(path = "api/v1/products")
public class ProductApi {
    @Autowired
    private ProductService productService;


    @GetMapping
    public ResponseEntity<Page<Product>> getList(@RequestParam(defaultValue = "1") int pageIndex, @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(productService.getList(pageIndex - 1, pageSize));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Product> findById(@PathVariable String id) {
        Optional<Product> optional = productService.findById(id);
        return optional.map(product -> ResponseEntity.status(HttpStatus.FOUND).body(product)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Product> Update(@PathVariable String id,@RequestBody Product update) {
        Optional<Product> optional = productService.findById(id);
        if (!optional.isPresent()) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(productService.update(optional,update));
    }
}
