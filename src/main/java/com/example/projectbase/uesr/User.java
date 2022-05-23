package com.example.projectbase.uesr;

import com.example.projectbase.Cart.shoppingCart.ShoppingCart;
import com.example.projectbase.baseEntity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(generator = "custom-name")
    @GenericGenerator(name = "custom-name",strategy = "com.example.projectbase.util.CustomId",parameters = @Parameter(name = "prefix",value = "USER"))
    private String id;
    private String username;
    private String hashPass;
    private String name;
    private String avatar;
    private int role;

}
