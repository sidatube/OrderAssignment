package com.example.projectbase.orderDetail;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailId implements Serializable {
    private String orderId;
    private String productId;

}
