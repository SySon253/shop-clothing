package vn.com.shop.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryAdjustmentRequestDTO {

    private Long variantId;

    private Integer quantity;

    private InventoryMovementType movementType;

    private String referenceCode;

    private String reason;

}