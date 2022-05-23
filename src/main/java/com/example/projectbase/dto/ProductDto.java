package com.example.projectbase.dto;

import com.example.projectbase.baseEntity.BaseEntity;
import com.example.projectbase.category.Category;
import com.example.projectbase.enumAll.ProductStatus;
import com.example.projectbase.product.Product;
import com.example.projectbase.util.StringHelper;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.example.projectbase.baseEntity.BaseEntity;
import com.example.projectbase.category.Category;
import com.example.projectbase.enumAll.ProductStatus;
import com.example.projectbase.orderDetail.OrderDetail;
import com.example.projectbase.util.StringHelper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDto {
    private String id;
    private String name;
    private String slug;
    private String thumbnails;
    private String detail;
    private BigDecimal price;
    private String description;
    private int status;



}
