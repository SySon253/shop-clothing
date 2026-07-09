package vn.com.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "product")
@Getter
@Setter
public class ProductEntity extends BaseEntity {
    private String name;

    @Column(unique = true)
    private String slug;

    @Lob
    private String description;
    private String brand;
    private Boolean active = true;

    @ManyToOne
    private CategoryEntity category;

    @OneToMany(mappedBy = "product")
    private Set<ProductImageEntity> images = new HashSet<>();

    @OrderBy("id ASC")
    @OneToMany(mappedBy = "product")
    private Set<ProductVariantEntity> variants = new HashSet<>();
}
