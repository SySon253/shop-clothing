package vn.com.shop.dto.order;

import lombok.Getter;
import lombok.Setter;
import vn.com.shop.dto.BaseResponseDTO;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderResponseDTO extends BaseResponseDTO {
    private String orderCode;
    private String receiverName;
    private String phone;
    private String address;
    private BigDecimal totalAmount;
    private String status;
    private List<OrderItemResponseDTO> items;
}