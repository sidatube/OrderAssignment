package com.example.projectbase.product;

import com.example.projectbase.baseEntity.BaseEntity;
import com.example.projectbase.category.Category;
import com.example.projectbase.enumAll.ProductStatus;
import com.example.projectbase.orderDetail.OrderDetail;
import com.example.projectbase.util.StringHelper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "products")
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(generator = "prod-generator")
    @GenericGenerator(name = "prod-generator",
            parameters = @Parameter(name = "prefix", value = "PRODUCT"),
            strategy = "com.example.projectbase.util.CustomId")
    private String id;
    private String name;
    private String slug;
    private String thumbnails;
    private String detail;
    private BigDecimal price;
    private String description;

    private String categoryId;

    public String getSlug() {
        return StringHelper.toSlug(name,id);
    }

    @ManyToOne
    @JoinColumn(name = "categoryId", insertable = false, updatable = false)
    private Category category;
    //    @Enumerated(EnumType.ORDINAL)
//    private ProductStatus status;
    private int status;
    @Transient
    private ProductStatus productStatus;

    @PostLoad
    void getOut() {
        this.productStatus = ProductStatus.of(status);
    }


    @PrePersist
    void beforeSave() {
        this.status = this.productStatus.getValue();

    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
