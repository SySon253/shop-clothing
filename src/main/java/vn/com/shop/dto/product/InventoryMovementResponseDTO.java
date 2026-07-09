package vn.com.shop.dto.product;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class InventoryMovementResponseDTO {
    private Long id;

    private String sku;

    private String productName;

    private String size;

    private String color;

    private InventoryMovementType movementType;

    private Integer quantityBefore;

    private Integer quantityChange;

    private Integer quantityAfter;

    private String reason;

    private String referenceCode;

    private LocalDateTime createdDate;

}