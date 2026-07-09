package vn.com.shop.dto.order;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderCreateResponseDTO {
    private Long orderId;

    private String paymentUrl;

    private OrderResponseDTO order;
}
