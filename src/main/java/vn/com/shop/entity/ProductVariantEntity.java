package vn.com.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "product_variants")
@Getter
@Setter
public class ProductVariantEntity extends BaseEntity{
    @Column(unique = true)
    private String sku;
    private BigDecimal price;
    private BigDecimal discountPrice;
    @Column(nullable = false)
    private Integer stock = 0;          // On Hand

    @Column(nullable = false)
    private Integer reservedStock = 0;  // Reserved

    @Column(nullable = false)
    private Integer sold = 0;           // Lifetime Sold

    @Column(nullable = false)
    private Integer reorderLevel = 20;
    private String size;
    private String color;
    @ManyToOne
    private ProductEntity product;
    @OneToMany(mappedBy = "variant")
    private Set<InventoryLogEntity> inventoryLogs;
}
