package com.example.projectbase.orderDetail;

import com.example.projectbase.order.Order;
import com.example.projectbase.product.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity(name = "orders_details")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {
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
    private BigDecimal unitPrice;
    private int quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetail that = (OrderDetail) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
