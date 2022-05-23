package com.example.projectbase.Cart.shoppingCart;

import com.example.projectbase.Cart.cartItem.CartItem;
import com.example.projectbase.uesr.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "ShoppingCart")
public class ShoppingCart {
    @Id
    @GeneratedValue(generator = "prod-generator")
    @GenericGenerator(name = "prod-generator",
            parameters = @Parameter(name = "prefix", value = "CART"),
            strategy = "com.example.projectbase.util.CustomId")
    private String id;
    @Column()
    private String userId;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId",updatable = false,insertable = false)
    @JsonBackReference
    private User user;
    private BigDecimal totalPrice;
    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<CartItem> cartItems;
    public void addTotalPrice(CartItem cartItem){
        if (this.totalPrice==null){
            this.totalPrice = new BigDecimal(0);
        }
        BigDecimal qtyInDecimal = new BigDecimal(cartItem.getQuantity());
        this.totalPrice=this.totalPrice.add(cartItem.getUnitPrice().multiply(qtyInDecimal));
    }
    public void resetCart(){
        cartItems = new HashSet<>();
    }
}
