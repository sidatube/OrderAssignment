package com.example.projectbase.Cart.shoppingCart;

import com.example.projectbase.Cart.cartItem.CartItem;
import com.example.projectbase.Cart.cartItem.CartItemId;
import com.example.projectbase.dto.CartItemDto;
import com.example.projectbase.dto.ShoppingCartDto;
import com.example.projectbase.product.Product;
import com.example.projectbase.product.ProductRepository;
import com.example.projectbase.uesr.User;
import com.example.projectbase.uesr.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShoppingCartService {
    @Autowired
    ShoppingCartRepository shoppingCartRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;


    public List<ShoppingCartDto> findAll(){
        List<ShoppingCart> shoppingCarts= shoppingCartRepository.findAll();
//
        return shoppingCarts.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    public List<ShoppingCart> findAllBase(){
        return shoppingCartRepository.findAll();
    }

    public ShoppingCartDto save(ShoppingCartDto shoppingCartDto){
        ShoppingCart shoppingCart =convertToBase(shoppingCartDto);
        Optional<User> optional= userRepository.findById(shoppingCart.getUserId());
        if (!optional.isPresent()){
            return null;
        }
        shoppingCart.setUser(optional.get());
        this.getInfoProductInCart(shoppingCart);
        return convertToDto(shoppingCartRepository.save(shoppingCart));
    }


    private void getInfoProductInCart(ShoppingCart shoppingCart){
        shoppingCart.setTotalPrice(new BigDecimal(0));
        for (CartItem item: shoppingCart.getCartItems()
        ) {
            Optional<Product> optional = productRepository.findById(item.getId().getProductId());
            if (!optional.isPresent()){
                continue;
            }
            Product product = optional.get();
            item.getId().setShoppingCartId(shoppingCart.getId());
            item.setShoppingCart(shoppingCart);
            item.setProductName(product.getName());
            item.setProductThumbnails(product.getThumbnails());
            item.setUnitPrice(product.getPrice());
            shoppingCart.addTotalPrice(item);
        }
    }
    private ShoppingCartDto convertToDto(ShoppingCart shoppingCart) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ShoppingCartDto shoppingCartDto = modelMapper.map(shoppingCart, ShoppingCartDto.class);
        shoppingCartDto.setCartItems(CartItemDto.convertToDto(shoppingCart.getCartItems()));
        return shoppingCartDto ;
    }
    private ShoppingCart convertToBase (ShoppingCartDto shoppingCartDto){
        ShoppingCart shoppingCart = modelMapper.map(shoppingCartDto,ShoppingCart.class);
        shoppingCart.setCartItems(CartItemDto.convertToBase(shoppingCartDto.getCartItems()));
        return shoppingCart ;
    }

    public ShoppingCartDto findByUserId(String userId) {
        Optional<ShoppingCart> optional = shoppingCartRepository.findByUserId(userId);

        return optional.map(this::convertToDto).orElse(null);
    }

    public ShoppingCartDto updateByUserId(String userId, ShoppingCartDto shoppingCartDto) {
        Optional<ShoppingCart> optional = shoppingCartRepository.findByUserId(userId);
        ShoppingCart update = convertToBase(shoppingCartDto);

        if (!optional.isPresent()){
            return null;
        }
        ShoppingCart exist = optional.get();
        exist.setCartItems(updateCart(update.getCartItems(),exist));
        getInfoProductInCart(exist);
        return convertToDto(shoppingCartRepository.save(exist));
    }
    private Set<CartItem> updateCart(Set<CartItem> updateSet,ShoppingCart exist ){
        Set<CartItem> cartItems = new HashSet<>();
        for (CartItem item: updateSet
             ) {
           Optional<Product> optional = productRepository.findById(item.getId().getProductId());
            if (!optional.isPresent()){
                continue;
            }
//            Product product = optional.get();
//            Chỗ này để check sản phẩm  hết chưa hết thì skip còn thì add, giỏ hàng có số sp lớn hơn kho hàng thì sao
            item.getId().setShoppingCartId(exist.getId());
            item.setShoppingCart(exist);
            cartItems.add(item);
        }
        return  cartItems;
    }

    public ShoppingCartDto updateItem(String userId, CartItemDto update) {
        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findByUserId(userId);
        Optional<Product> optionalProduct = productRepository.findById(update.getProductId());
        if (!optionalProduct.isPresent()){
            return  null;
        }
        if (update.getQuantity()<=0){
            update.setQuantity(1);
        }
        CartItem item = CartItem.builder().quantity(update.getQuantity()).id(new CartItemId()).build();
        item.getId().setProductId(update.getProductId());


        ShoppingCart exist = ShoppingCart.builder().id("").userId(userId).cartItems(new HashSet<>()).build();

        if (optionalShoppingCart.isPresent()){
            exist = optionalShoppingCart.get();
        }
        item.getId().setShoppingCartId(exist.getId());
//        item.setProduct(optionalProduct.get());
        item.setShoppingCart(exist);
        if (exist.getCartItems().contains(item)){
          List<CartItem> cartItems = new ArrayList<>(exist.getCartItems());
          CartItem find =cartItems.get(cartItems.indexOf(item));
          find.setQuantity(find.getQuantity()+item.getQuantity());
          exist.setCartItems(new HashSet<>(cartItems));
        }else {
            exist.getCartItems().add(item);
        }
        getInfoProductInCart(exist);
        return convertToDto(shoppingCartRepository.save(exist));
    }
}
