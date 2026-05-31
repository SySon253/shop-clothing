package vn.com.shop.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderLineDTO {
    private Long id;

    private Long orderId;

    private Long productItemId;

    private Integer qty;

    private BigDecimal price;

    private String productName;

    private String variantInfo;
}
