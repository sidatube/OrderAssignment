package com.example.projectbase.enumAll;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum ProductStatus {
//    DeActive, Active, Deleted, Undefined;

    DeActive(0),
    Active(1),
    Deleted(-1),
    Undefined(-2);
    private  int value;
    ProductStatus(int val){
        this.value=val;
    }
    public static ProductStatus of(int value){
        for (ProductStatus status:ProductStatus.values()){
            if (status.getValue()==value){
                return status;
            }
        }
        return ProductStatus.Undefined;
    }

}