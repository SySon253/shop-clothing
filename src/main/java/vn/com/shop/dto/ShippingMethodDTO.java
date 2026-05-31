package vn.com.shop.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ShippingMethodDTO {
    private Long id;

    private String name;

    private BigDecimal price;
}
