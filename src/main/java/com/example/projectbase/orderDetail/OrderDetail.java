package com.example.projectbase.orderDetail;

import com.example.projectbase.baseEntity.BaseEntity;
import com.example.projectbase.order.Order;
import com.example.projectbase.product.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "orders_details")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail extends BaseEntity {
    @EmbeddedId
    private OrderDetailId id;
    @ManyToOne
    @JoinColumn(name = "orderId",nullable = false)
    @MapsId("orderId")
    @JsonBackReference
    private Order order;
    @ManyToOne
    @JoinColumn(name = "productId",nullable = false)
    @MapsId("productId")
    @JsonBackReference
    private Product product;
    private double unitPrice;
    private int quantity;
}
