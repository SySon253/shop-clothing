package vn.com.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "inventory_log")
@Getter
@Setter
public class InventoryLogEntity extends BaseEntity {

    @ManyToOne
    private ProductVariantEntity variant;

    @Enumerated(EnumType.STRING)
    private InventoryAction action;

    private Integer quantity;
    private String note;
}