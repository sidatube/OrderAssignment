package com.example.projectbase.order;

import com.example.projectbase.orderDetail.OrderDetail;
import com.example.projectbase.orderDetail.OrderDetailId;
import com.example.projectbase.product.Product;
import com.example.projectbase.product.ProductRepository;
import com.example.projectbase.product.ProductService;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    public Order save(Order order){
        return orderRepository.save(order);
    }
    public List<Order> saveAll(List<Order> orders){
        return orderRepository.saveAll(orders);
    }
    public List<Order> getList(){
        return orderRepository.findAll();
    }
    public Optional<Order> findById(int id){
        return orderRepository.findById(id);
    }

    public Order addOrder() {
        List<Product> products = productRepository.findAll();
        Order order = new Order();
        OrderDetail orderDetail = new OrderDetail();
        OrderDetailId orderDetailId = new OrderDetailId();
        Set<OrderDetail> orderDetailList = new HashSet<>();

        Faker faker = new Faker();
        order.setCustomerId(faker.number().randomDigitNotZero());
        Product randomProduct=products.get(faker.random().nextInt(0,products.size()-1));
        orderDetailId.setOrderId(order.getId());
        orderDetailId.setProductId(randomProduct.getId());
        orderDetail.setId(orderDetailId);
        orderDetail.setUnitPrice(faker.number().randomDouble(2,10,100));
        orderDetail.setQuantity(faker.number().randomDigitNotZero());
        orderDetail.setOrder(order);
        orderDetail.setProduct(randomProduct);
        orderDetailList.add(orderDetail);
        order.setOrderDetails(orderDetailList);
        double total = 0;
        for (OrderDetail item : order.getOrderDetails()
             ) {
            total+= item.getQuantity()*item.getUnitPrice();
        }
        order.setTotalPrice(total);

        return orderRepository.save(order);
    }
}
