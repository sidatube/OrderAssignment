package com.example.projectbase.search;

import com.example.projectbase.order.Order;
import com.example.projectbase.orderDetail.OrderDetail;
import com.example.projectbase.product.Product;
import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
public class GenericSpecification<T> implements Specification<T> {
    private final SearchCriteria searchCriteria;

    public GenericSpecification(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    //criteriaBuilder xử lí toán tử
    //Root lấy ra trường
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        switch (searchCriteria.getOperator()) {
            case Equals:
                return criteriaBuilder.equal(root.get(searchCriteria.getKey()), searchCriteria.getValue());
            case Not_Equals:
                return criteriaBuilder.notEqual(root.get(searchCriteria.getKey()), searchCriteria.getValue());
            case Greater_Than:
                if (root.get(searchCriteria.getKey()).getJavaType() == LocalDateTime.class)
                    return criteriaBuilder.greaterThan(root.get(searchCriteria.getKey()), (LocalDateTime) searchCriteria.getValue());
                return criteriaBuilder.greaterThan(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString());

            case Greater_Than_Or_Equals:
                if (root.get(searchCriteria.getKey()).getJavaType() == LocalDateTime.class)
                    return criteriaBuilder.greaterThanOrEqualTo(root.get(searchCriteria.getKey()), (LocalDateTime) searchCriteria.getValue());
                return criteriaBuilder.greaterThanOrEqualTo(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString());

            case Less_Than:
                if (root.get(searchCriteria.getKey()).getJavaType() == LocalDateTime.class)
                    return criteriaBuilder.lessThan(root.get(searchCriteria.getKey()), (LocalDateTime) searchCriteria.getValue());
                return criteriaBuilder.lessThan(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString());
            case Less_Than_Or_Equals:
                if (root.get(searchCriteria.getKey()).getJavaType() == LocalDateTime.class)
                    return criteriaBuilder.lessThanOrEqualTo(root.get(searchCriteria.getKey()), (LocalDateTime) searchCriteria.getValue());
                return criteriaBuilder.lessThanOrEqualTo(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString());


            case Like:
                return criteriaBuilder.like(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString());


            case In:
//                return criteriaBuilder.notEqual(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString());

            default:
                return null;

        }
    }
}
