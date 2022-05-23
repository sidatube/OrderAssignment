package com.example.projectbase.dto;

import com.example.projectbase.Cart.cartItem.CartItem;
import com.example.projectbase.Cart.shoppingCart.ShoppingCart;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;


import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDto {
    private String productId;
    private String productName;
    private String productThumbnails;
    private int quantity;
    private BigDecimal unitPrice;
    private static ModelMapper modelMapper;
    static {
        modelMapper=new ModelMapper();
    }


    public static Set<CartItemDto> convertToDto(Set<CartItem> cartItems) {

        return cartItems.stream().map(cartItem -> modelMapper.map(cartItem,CartItemDto.class)).collect(Collectors.toSet());
    }
    public static Set<CartItem> convertToBase (Set<CartItemDto> cartItemDtos){

        return cartItemDtos.stream().map(cartItemDto -> modelMapper.map(cartItemDto,CartItem.class)).collect(Collectors.toSet());

    }
}
