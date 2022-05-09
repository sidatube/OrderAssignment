package com.example.projectbase.dataSeed;

import com.example.projectbase.category.Category;
import com.example.projectbase.category.CategoryService;
import com.example.projectbase.order.Order;
import com.example.projectbase.order.OrderService;
import com.example.projectbase.orderDetail.OrderDetailService;
import com.example.projectbase.product.Product;
import com.example.projectbase.product.ProductService;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataSeed implements CommandLineRunner {
    private Faker faker = new Faker();
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private ProductService productService;

    @Override
    public void run(String... args) throws Exception {
        //Category
        if(categoryService.getList().isEmpty()){
            List<Category> categories = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                Category cate = new Category();
                cate.setName(faker.pokemon().name());
                categories.add(cate);
            }
            categoryService.saveAll(categories);
        }
        if(productService.getList().isEmpty()){
            List<Product> products = new ArrayList<>();
            List<Category> categories = categoryService.getList();
            for (int i = 0; i < 100; i++) {
                Product product = new Product();
                product.setName(faker.pokemon().name());
                product.setPrice(faker.number().randomDouble(2,10,100));
                product.setCategoryId(categories.get(faker.number().numberBetween(0, categories.size()-1)).getId());

//                product.setCategory(categories.get(faker.number().numberBetween(0, categories.size()-1)));
                products.add(product);
            }
            productService.saveAll(products);
        }

    }
}
