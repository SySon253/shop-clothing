package vn.com.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import vn.com.shop.dto.product.InventoryMovementType;

@Entity
@Table(name = "inventory_movements")
@Getter
@Setter
public class InventoryMovementEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "variant_id", nullable = false)
    private ProductVariantEntity variant;

    @Enumerated(EnumType.STRING)
    private InventoryMovementType movementType;

    private Integer quantityBefore;

    private Integer quantityChange;

    private Integer quantityAfter;

    private String reason;

    private String referenceCode;
}