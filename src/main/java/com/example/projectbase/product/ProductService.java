package com.example.projectbase.product;

import com.example.projectbase.category.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Page<Product> getList(int pageIndex, int pageSize){
        Pageable item;
        try {
             item = PageRequest.of(pageIndex, pageSize);
        }catch (Exception e){
             item = null;
        }
        return productRepository.findAll(item);
    }
    public Optional<Product> findById(String id){
        return productRepository.findById(id);
    }
    public Product update(Optional<Product>optional,Product update ){
        Product exitsProduct = optional.get();
        exitsProduct.setThumbnails(update.getThumbnails());
        exitsProduct.setPrice(update.getPrice());
        exitsProduct.setStatus(update.getStatus());
        exitsProduct.setDescription(update.getDescription());
        exitsProduct.setName(update.getName());
        exitsProduct.setCategoryId(update.getCategoryId());
        return productRepository.save(exitsProduct);
    }
}
