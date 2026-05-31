package vn.com.shop.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InventoryLogDTO {
    private Long id;

    private Long productItemId;

    private Integer changeAmount;

    private String type;

    private LocalDateTime createdAt;
}
