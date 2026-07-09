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
    @JoinColumn(
            name = "order_id"
    )
    private OrderEntity order;



    // mã giao dịch của hệ thống
    private String transactionId;



    // VNPAY
    private String paymentMethod;



    private Double amount;



    // PENDING
    // SUCCESS
    // FAILED
    private String paymentStatus;



    // mã giao dịch VNPay trả về
    private String vnpTransactionNo;



    private String bankCode;


}