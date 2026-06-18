package vn.com.shop.dto.order;

import lombok.Getter;
import lombok.Setter;
import vn.com.shop.dto.BaseResponseDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class OrderResponseDTO extends BaseResponseDTO {
    private String orderCode;
    private String receiverName;
    private String phone;
    private String address;
    private BigDecimal totalAmount;
    private PaymentMethod paymentMethod;
    private Set<OrderItemResponseDTO> items;
}