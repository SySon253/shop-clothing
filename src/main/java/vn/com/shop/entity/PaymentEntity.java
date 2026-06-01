package vn.com.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "payment")
@Getter
@Setter
public class PaymentEntity extends BaseEntity {
    @OneToOne
    private OrderEntity order;

    private String transactionId;
    private String paymentMethod;
    private Double amount;
    private String paymentStatus;
}
