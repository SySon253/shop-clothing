package vn.com.shop.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentTransactionDTO {
    private Long id;

    private Long orderId;

    private BigDecimal amount;

    private String provider;

    private String status;

    private String transactionId;

    private LocalDateTime createdAt;
}
