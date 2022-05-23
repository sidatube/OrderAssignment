package com.example.projectbase.Cart.shoppingCart;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,String> {
    Optional<ShoppingCart> findByUserId(String userId);
}
