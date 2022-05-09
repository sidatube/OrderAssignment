package com.example.projectbase.order;

import com.example.projectbase.Cart.cartItem.CartItem;
import com.example.projectbase.baseEntity.BaseEntity;
import com.example.projectbase.enumAll.OrderStatus;
import com.example.projectbase.orderDetail.OrderDetail;
import com.example.projectbase.uesr.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "orders")
@ToString
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(generator = "prod-generator")
    @GenericGenerator(name = "prod-generator",
            parameters = @Parameter(name = "prefix", value = "ORDER"),
            strategy = "com.example.projectbase.util.CustomId")
    private String id;
    private String  userId;
    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "userId",insertable = false,updatable = false)
    private User user;
    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderDetail> orderDetails;
    private BigDecimal totalPrice;
    private String shipName;
    private String shipAddress;
    private String shipPhone;
    @Enumerated(value = EnumType.ORDINAL)
    private OrderStatus status;
    public void addTotalPrice(OrderDetail orderDetail){
        if (this.totalPrice==null){
            this.totalPrice = new BigDecimal(0);
        }
        BigDecimal qtyInDecimal = new BigDecimal(orderDetail.getQuantity());
        this.totalPrice=this.totalPrice.add(orderDetail.getUnitPrice().multiply(qtyInDecimal));
    }

}
