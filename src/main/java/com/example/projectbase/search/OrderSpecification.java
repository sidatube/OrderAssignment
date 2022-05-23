package com.example.projectbase.search;

import com.example.projectbase.order.Order;
import com.example.projectbase.orderDetail.OrderDetail;
import com.example.projectbase.product.Product;
import com.example.projectbase.uesr.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.Locale;

public class OrderSpecification extends GenericSpecification<Order> {

    public OrderSpecification(SearchCriteria searchCriteria) {
        super(searchCriteria);
    }

    //criteriaBuilder xử lí toán tử
    //Root lấy ra trường

    @Override
    public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        switch (getSearchCriteria().getOperator()) {
            case Join:
                switch (getSearchCriteria().getKey().toLowerCase(Locale.ROOT)){
                    case "product":
                        Join<OrderDetail, Product> orderDetailProductJoin = root.join("orderDetails").join("product");
                        return criteriaBuilder.or(
                                // tìm trong order bản ghi có id giống giá trị truyền vào
//                        criteriaBuilder.like(root.get("id"), "%" + getSearchCriteria().getValue() + "%"),
                                // hoặc tìm trong bảng product bản ghi có name giống với giá trị
                                criteriaBuilder.like(criteriaBuilder.lower(orderDetailProductJoin.get("name")),"%"+getSearchCriteria().getValue()+"%"));
                    case "user":
                        Join<Order, User> userJoin = root.join("user");
                        return criteriaBuilder.or(
                                criteriaBuilder.like(criteriaBuilder.lower(userJoin.get("username")),"%"+getSearchCriteria().getValue()+"%"),
                                criteriaBuilder.like(criteriaBuilder.lower(userJoin.get("name")),"%"+getSearchCriteria().getValue()+"%")
                        );

                    default:
                        break;
                }


            default:
                break;

        }
        return super.toPredicate(root, query, criteriaBuilder);
    }
}
