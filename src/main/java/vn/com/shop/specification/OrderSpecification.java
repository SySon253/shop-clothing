package vn.com.shop.specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import vn.com.shop.dto.request.OrderRequestFilter;
import vn.com.shop.entity.OrderEntity;

import java.util.ArrayList;
import java.util.List;

public class OrderSpecification {


    public static Specification<OrderEntity> filter(
            OrderRequestFilter filter
    ){

        return (root, query, cb) -> {


            List<Predicate> predicates =
                    new ArrayList<>();


            // ==========================
            // SEARCH KEYWORD
            // ==========================

            if(filter.getKeyword()!=null
                    &&
                    !filter.getKeyword().isEmpty()){


                String keyword =
                        "%"
                                + filter.getKeyword()
                                .toLowerCase()
                                + "%";


                Predicate receiver =
                        cb.like(
                                cb.lower(
                                        root.get("receiverName")
                                ),
                                keyword
                        );


                Predicate phone =
                        cb.like(
                                root.get("phone"),
                                keyword
                        );


                Predicate orderCode =
                        cb.like(
                                cb.lower(
                                        root.get("orderCode")
                                ),
                                keyword
                        );


                predicates.add(
                        cb.or(
                                receiver,
                                phone,
                                orderCode
                        )
                );

            }



            // ==========================
            // STATUS
            // ==========================

            if(filter.getStatus()!=null){


                predicates.add(
                        cb.equal(
                                root.get("status"),
                                filter.getStatus()
                        )
                );


            }



            return cb.and(
                    predicates.toArray(
                            new Predicate[0]
                    )
            );

        };

    }

}