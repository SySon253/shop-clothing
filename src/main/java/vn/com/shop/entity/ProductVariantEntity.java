package vn.com.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "product_variants")
@Getter
@Setter
public class ProductVariantEntity extends BaseEntity{
    @Column(unique = true)
    private String sku;

    private Double price;

    private Double discountPrice;

    private Integer stock;

    private Integer reservedStock;

    private String size;

    private String color;

    @ManyToOne
    private ProductEntity product;

    @OneToMany(mappedBy = "variant")
    private Set<InventoryLogEntity> inventoryLogs;
}
