package com.example.projectbase.Cart.shoppingCart;

import com.example.projectbase.dto.CartItemDto;
import com.example.projectbase.dto.ShoppingCartDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/cart")
public class ShoppingCartApi {
    @Autowired
    ShoppingCartService shoppingCartService;
//    @Autowired


    @GetMapping
    public ResponseEntity<List<ShoppingCartDto>> getList(){
        return ResponseEntity.ok(shoppingCartService.findAll());
    }
    @PostMapping
    public ResponseEntity<ShoppingCartDto> save(@RequestBody ShoppingCartDto shoppingCartDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(shoppingCartService.save(shoppingCartDto));
    }
    @GetMapping(path = "/{userId}")
    public ResponseEntity<ShoppingCartDto> getByUserId(@PathVariable String userId){
        ShoppingCartDto shoppingCartDto = shoppingCartService.findByUserId(userId);
        return ResponseEntity.status(shoppingCartDto!=null?HttpStatus.FOUND:HttpStatus.NOT_FOUND).body(shoppingCartDto);
    }
    @PutMapping(path = "/{userId}")
    public ResponseEntity<ShoppingCartDto> updateByUserId(@PathVariable String userId,@RequestBody ShoppingCartDto update){
        ShoppingCartDto shoppingCartDto = shoppingCartService.findByUserId(userId);
        return ResponseEntity.status(shoppingCartDto!=null?HttpStatus.FOUND:HttpStatus.NOT_FOUND).body(shoppingCartService.updateByUserId(userId,update));
    }
    @PutMapping(path = "addProduct/{userId}")
    public ResponseEntity<ShoppingCartDto> updateCart(@PathVariable String userId,@RequestBody CartItemDto update){

        ShoppingCartDto shoppingCartDto = shoppingCartService.findByUserId(userId);

        return ResponseEntity.status(HttpStatus.FOUND).body(shoppingCartService.updateItem(userId,update));
    }




}
