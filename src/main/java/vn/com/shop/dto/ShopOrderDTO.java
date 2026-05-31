package vn.com.shop.dto;

import lombok.Data;
import vn.com.shop.dto.order.OrderLineDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ShopOrderDTO {
    private Long id;

    private Long userId;

    private Long shippingAddressId;

    private Long shippingMethodId;

    private BigDecimal orderTotal;

    private Long orderStatusId;

    private LocalDateTime paidAt;

    private LocalDateTime shippedAt;

    private LocalDateTime createdAt;

    private List<OrderLineDTO> orderLines;
}
