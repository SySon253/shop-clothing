package vn.com.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import vn.com.shop.dto.order.PaymentMethod;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class OrderEntity extends BaseEntity {
    @ManyToOne
    private UserEntity user;

    private String receiverName;
    private String phone;
    private String address;
    private BigDecimal totalAmount;


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItemEntity> items = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToOne(
            mappedBy = "order",
            cascade = CascadeType.ALL
    )
    private PaymentEntity payment;
}

