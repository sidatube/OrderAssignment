package com.example.projectbase.order;

import com.example.projectbase.ProjectBaseApplication;
import com.example.projectbase.category.Category;
import com.example.projectbase.category.CategoryService;
import com.example.projectbase.config.H2JpaConfig;
import com.example.projectbase.enumAll.ProductStatus;
import com.example.projectbase.orderDetail.OrderDetail;
import com.example.projectbase.orderDetail.OrderDetailId;
import com.example.projectbase.product.Product;
import com.example.projectbase.product.ProductRepository;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Stream;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ProjectBaseApplication.class, H2JpaConfig.class})
@ActiveProfiles("test")
public class OrderRepositoryTest {
    Faker faker = new Faker();
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    CategoryService categoryService;

    @Before
    public void before() throws Exception {
        if(categoryService.getList().isEmpty()){
            List<Category> categories = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                Category cate = new Category();
                cate.setName(faker.pokemon().name());
                categories.add(cate);
            }
            categoryService.saveAll(categories);
        }
        if (productRepository.findAll().isEmpty()) {
            List<Product> products = new ArrayList<>();
            List<Category> categories = categoryService.getList();
            for (int i = 0; i < 10; i++) {
                Product product = new Product();
                product.setName("Bố là tất");
                product.setPrice(BigDecimal.valueOf(faker.number().randomDouble(2, 10, 100)));
                product.setCategoryId(categories.get(faker.number().numberBetween(0, categories.size()-1)).getId());
                product.setProductStatus(ProductStatus.Active);
                products.add(product);
            }
            productRepository.saveAll(products);
        }
    }

    @Test
    public void SaveSimple() {
        Order order = new Order();
        order.setId("ABC");
        List<Product> products = productRepository.findAll();
        Set<OrderDetail> orderDetailSet = new HashSet<>();

        System.out.println(products.size()+"-------");
        Product randomProduct01 = products.get(faker.random().nextInt(0, products.size() - 1));
        Product randomProduct02 = products.get(products.size() - 1);
        System.out.println(randomProduct01);
        System.out.println(randomProduct02);
        while (randomProduct01==randomProduct02){
            randomProduct02 =products.get(faker.random().nextInt(0, products.size() - 1));
        }
        OrderDetail orderDetail01 = new OrderDetail();

        orderDetail01.setId(new OrderDetailId(order.getId(), randomProduct01.getId()));
        orderDetail01.setOrder(order);
        orderDetail01.setProduct(randomProduct01);
        orderDetail01.setUnitPrice(randomProduct01.getPrice());
        orderDetail01.setQuantity(faker.number().randomDigitNotZero());
        orderDetailSet.add(orderDetail01);


        OrderDetail orderDetail02 = new OrderDetail();

        orderDetail02.setId(new OrderDetailId(order.getId(), randomProduct02.getId()));
        orderDetail02.setUnitPrice(randomProduct02.getPrice());
        orderDetail02.setQuantity(faker.number().randomDigitNotZero());
        orderDetail02.setOrder(order);
        orderDetail02.setProduct(randomProduct02);
        orderDetailSet.add(orderDetail02);
        order.setTotalPrice(new BigDecimal(0));
        order.setOrderDetails(orderDetailSet);
        for (OrderDetail orderDetailItem:order.getOrderDetails()){
            order.setTotalPrice(order.getTotalPrice().add(orderDetailItem.getUnitPrice().multiply(new BigDecimal(orderDetailItem.getQuantity()))));
        }

        orderRepository.save(order);
        Order savedOrder = orderRepository.findAll().get(0);
        System.out.println(savedOrder.getId());
        System.out.println(savedOrder.getTotalPrice());
        System.out.println(savedOrder.getOrderDetails().size());
        for (OrderDetail od :
                savedOrder.getOrderDetails()) {
            System.out.println(od.getQuantity());
            System.out.println(od.getUnitPrice());
            System.out.println(od.getProduct().getName());
        }    }
}