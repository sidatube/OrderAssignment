package com.example.projectbase.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    public Category save(Category category){
        return categoryRepository.save(category);
    }
    public List<Category> saveAll(List<Category> categories){
        return categoryRepository.saveAll(categories);
    }
    public List<Category> getList(){
        return categoryRepository.findAll();
    }
    public Optional<Category> findById(int id){
        return categoryRepository.findById(id);
    }
    public Category update(Optional<Category> optional,Category update){
        Category item = optional.get();
        item.setName(update.getName());
        item.setStatus(update.getStatus());
        return categoryRepository.save(item);
    }
}
