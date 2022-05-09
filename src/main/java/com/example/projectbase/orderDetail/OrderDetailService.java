package com.example.projectbase.orderDetail;

import com.example.projectbase.category.Category;
import com.example.projectbase.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    public OrderDetail save(OrderDetail orderDetail){
        return orderDetailRepository.save(orderDetail);
    }
    public List<OrderDetail> saveAll(List<OrderDetail> orderDetails){
        return orderDetailRepository.saveAll(orderDetails);
    }
    public List<OrderDetail> getList(){
        return orderDetailRepository.findAll();
    }
    public Optional<OrderDetail> findById(int id){
        return orderDetailRepository.findById(id);
    }
}
