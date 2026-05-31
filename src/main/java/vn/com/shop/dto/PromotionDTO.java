package vn.com.shop.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PromotionDTO {
    private Long id;

    private String name;

    private String description;

    private BigDecimal discountRate;

    private LocalDateTime startDate;

    private LocalDateTime endDate;
}
