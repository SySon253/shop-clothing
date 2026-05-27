package vn.com.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "product")
@Getter
@Setter
public class ProductEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductID")
    private int productID;

    private String productName;
    private String description;
    private String brand;
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL)
    private List<ProductVariantEntity> variants;

    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL)
    private List<ProductImageEntity> images;
}
