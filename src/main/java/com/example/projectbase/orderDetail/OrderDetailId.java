package com.example.projectbase.orderDetail;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class OrderDetailId implements Serializable {
    private int orderId;
    private int productId;

}
