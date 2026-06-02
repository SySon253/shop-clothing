package vn.com.shop.dto.payment;

import lombok.Getter;
import lombok.Setter;
import vn.com.shop.dto.BaseResponseDTO;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentResponseDTO extends BaseResponseDTO {
    private String transactionId;
    private String paymentMethod;
    private BigDecimal amount;
    private String paymentStatus;
}