package vn.com.shop.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "inventory_log")
public class InventoryLogEntity extends BaseEntity {

    @ManyToOne
    private ProductVariantEntity variant;

    @Enumerated(EnumType.STRING)
    private InventoryAction action;

    private Integer quantity;
    private String note;
}