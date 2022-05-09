package com.example.projectbase.product;

import com.example.projectbase.category.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    public Product save(Product order){
        return productRepository.save(order);
    }
    public List<Product> saveAll(List<Product> products){
        return productRepository.saveAll(products);
    }
    public List<Product> getList(){
        return productRepository.findAll();
    }
    public Optional<Product> findById(int id){
        return productRepository.findById(id);
    }
}
