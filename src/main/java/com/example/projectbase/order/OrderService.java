package com.example.projectbase.order;

import com.example.projectbase.orderDetail.OrderDetail;
import com.example.projectbase.orderDetail.OrderDetailId;
import com.example.projectbase.product.Product;
import com.example.projectbase.product.ProductRepository;
import com.example.projectbase.product.ProductService;
import com.example.projectbase.search.OrderSpecification;
import com.example.projectbase.search.SearchCriteria;
import com.example.projectbase.search.SearchCriteriaOperator;
import com.example.projectbase.util.StringHelper;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    public List<Order> getList(){
        return orderRepository.findAll();
    }
    public Optional<Order> findById(String  id){
        return orderRepository.findById(id);
    }

    public Object findAll(int page, int limit, String productName, String userName, String dateFrom, String dateTo, String userId, int status,boolean desc) {
        Specification<Order> specification = Specification.where(null);
        if (!userId.isEmpty()) {
            OrderSpecification spec = new OrderSpecification(new SearchCriteria("userId", SearchCriteriaOperator.Equals, userId));
            specification = specification.and(spec);
        }
        if (!productName.isEmpty()) {
            OrderSpecification spec = new OrderSpecification(new SearchCriteria("product", SearchCriteriaOperator.Join, productName));
            specification = specification.and(spec);
        }if (!dateFrom.isEmpty()) {
            LocalDateTime from = StringHelper.toDateTime(dateFrom);
            OrderSpecification spec = new OrderSpecification(new SearchCriteria("createdAt", SearchCriteriaOperator.Less_Than_Or_Equals, from));
            specification = specification.and(spec);
        }
        if (!dateTo.isEmpty()) {
            LocalDateTime to = StringHelper.toDateTime(dateTo);
            OrderSpecification spec = new OrderSpecification(new SearchCriteria("createdAt", SearchCriteriaOperator.Greater_Than_Or_Equals, to));
            specification = specification.and(spec);
        }
        if (!userName.isEmpty()) {
            OrderSpecification spec = new OrderSpecification(new SearchCriteria("user", SearchCriteriaOperator.Join, userName));
            specification = specification.and(spec);
        }
        specification = specification.and(new OrderSpecification(new SearchCriteria("status", SearchCriteriaOperator.Equals, status)));
        Sort sort =Sort.by("createdAt");
        if (desc) {
           sort= Sort.by("createAt").descending();
        }
        return orderRepository.findAll(specification, PageRequest.of(page-1,limit,sort ));

    }
}
