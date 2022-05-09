package com.example.projectbase.dataSeed;

import com.example.projectbase.Cart.shoppingCart.ShoppingCart;
import com.example.projectbase.category.Category;
import com.example.projectbase.category.CategoryRepository;
import com.example.projectbase.enumAll.OrderStatus;
import com.example.projectbase.enumAll.ProductStatus;
import com.example.projectbase.order.Order;
import com.example.projectbase.order.OrderRepository;
import com.example.projectbase.orderDetail.OrderDetail;
import com.example.projectbase.orderDetail.OrderDetailId;
import com.example.projectbase.orderDetail.OrderDetailService;
import com.example.projectbase.product.Product;
import com.example.projectbase.product.ProductRepository;
import com.example.projectbase.uesr.User;
import com.example.projectbase.uesr.UserRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class DataSeed implements CommandLineRunner {
    private final Faker faker;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    public DataSeed() {
        this.faker = new Faker();
    }

    @Override
    public void run(String... args) throws Exception {
//        List<String > strings = new ArrayList<String >(Arrays.asList("v","a"));
        //Category
        if (userRepository.findAll().isEmpty()){
            List<User> users =new ArrayList<>();
            for (int i = 0; i <100 ; i++) {
                User user = User.builder()
                        .id("")
                        .avatar(faker.avatar().image())
                        .username(faker.name().username())
                        .name(faker.name().lastName())
                        .hashPass(faker.random().toString())
                        .build();
                users.add(user);
            }
            userRepository.saveAll(users);
        }
        if(categoryRepository.findAll().isEmpty()){
            List<Category> categories = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                Category cate = new Category();
                cate.setName(faker.pokemon().name());
                categories.add(cate);
            }
            categoryRepository.saveAll(categories);
        }
        if(productRepository.findAll().isEmpty()){
            List<Product> products = new ArrayList<>();
            List<Category> categories = categoryRepository.findAll();
            for (int i = 0; i < 10; i++) {
                Product product = new Product();
                product.setName(faker.pokemon().name());
                product.setDescription(faker.lorem().characters());
                product.setDetail(faker.lorem().characters());
                product.setPrice(BigDecimal.valueOf(faker.number().numberBetween(100,1000)* 1000L));
                product.setCategoryId(categories.get(faker.number().numberBetween(0, categories.size()-1)).getId());
                product.setProductStatus(ProductStatus.Active);
                products.add(product);
            }
            productRepository.saveAll(products);
        }
        if (orderRepository.findAll().isEmpty()){
            List<Order> orders = new ArrayList<>();
            List<User> users = userRepository.findAll();
            List<Product> products = productRepository.findAll();
            for (int i = 0; i < 100 ; i++) {
                Order order = new Order();
                order.setId("ABC");
                User randomUser = users.get(faker.random().nextInt(0, users.size()-1));
                Set<OrderDetail> orderDetailSet = new HashSet<>();
                Set<String  > check = new HashSet<>();
                order.setUserId(randomUser.getId());
                order.setUser(randomUser);
                for (int ia = 0; ia <faker.random().nextInt(1,5) ; ia++) {
                    Product randomProduct = products.get(faker.random().nextInt(0, products.size() - 1));
                    if(check.contains(randomProduct.getId())){
                        continue;
                    };
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setId(new OrderDetailId(order.getId(), randomProduct.getId()));

                    orderDetail.setOrder(order);
                    orderDetail.setProduct(randomProduct);
                    orderDetail.setUnitPrice(randomProduct.getPrice());
                    orderDetail.setQuantity(faker.number().randomDigitNotZero());
                    orderDetailSet.add(orderDetail);
                    order.addTotalPrice(orderDetail);
                    check.add(randomProduct.getId());
                }
                order.setStatus(OrderStatus.Done);
                order.setOrderDetails(orderDetailSet);
                orders.add(order);
            }
           orderRepository.saveAll(orders);

        }

    }
}
