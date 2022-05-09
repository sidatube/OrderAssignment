package com.example.projectbase.product;

import com.example.projectbase.baseEntity.BaseEntity;
import com.example.projectbase.category.Category;
import com.example.projectbase.orderDetail.OrderDetail;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "products")
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private double price;
    private int categoryId;
    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "categoryId",insertable = false,updatable = false)
    private Category category;
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<OrderDetail> orderDetails;
}
