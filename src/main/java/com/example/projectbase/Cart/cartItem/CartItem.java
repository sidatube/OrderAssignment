package com.example.projectbase.Cart.cartItem;

import com.example.projectbase.Cart.shoppingCart.ShoppingCart;
import com.example.projectbase.product.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity(name = "cartItems")
public class CartItem {
    @EmbeddedId
    private CartItemId id;
    private String productName;
    private String productThumbnails;
    private int quantity;
    private BigDecimal unitPrice;
    @ManyToOne
    @MapsId("shoppingCartId")
    @JoinColumn(name = "shoppingCartId")
    @JsonBackReference
    private ShoppingCart shoppingCart;
//    @ManyToOne
//    @MapsId("productId")
//    @JoinColumn(name = "productId")
//    @JsonBackReference
//    private Product product;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem item = (CartItem) o;
        return Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
