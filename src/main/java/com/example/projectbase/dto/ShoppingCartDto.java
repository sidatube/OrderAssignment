package com.example.projectbase.dto;

import com.example.projectbase.Cart.cartItem.CartItem;
import com.example.projectbase.Cart.shoppingCart.ShoppingCart;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShoppingCartDto {
    private String id="";
    private String userId;
    private Set<CartItemDto> cartItems;
    private BigDecimal totalPrice;

    public Set<CartItem> changeToCart(){

        return CartItemDto.convertToBase(cartItems);
    }
    public ShoppingCart changeToShoppingCart() {

        return ShoppingCart.builder()
                .id(this.id)
                .userId(this.userId)
                .totalPrice(new BigDecimal(0))
                .cartItems(changeToCart())
                .build();
    }

}
