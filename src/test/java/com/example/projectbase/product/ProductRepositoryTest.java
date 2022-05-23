package com.example.projectbase.product;

import com.example.projectbase.ProjectBaseApplication;
import com.example.projectbase.config.H2JpaConfig;
import com.example.projectbase.enumAll.ProductStatus;
import com.example.projectbase.util.StringHelper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ProjectBaseApplication.class, H2JpaConfig.class} )
@ActiveProfiles("test")
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;
    @Test
    public void save(){
        Product product = new Product();
        product.setName("Product 01");
        product.setDescription("Product 01 description");
        product.setPrice(new BigDecimal(20));
        product.setProductStatus(ProductStatus.Deleted);
        productRepository.save(product);
        System.out.println(productRepository.findAll().size());
        Product product1 = productRepository.findAll().get(0);
        System.out.println(product1);

    }

}