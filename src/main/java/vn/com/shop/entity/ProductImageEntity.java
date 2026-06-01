package vn.com.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product_image")
@Getter
@Setter
public class ProductImageEntity extends BaseEntity{// Bảng này không có trường audit nên không cần extend
    private String imageUrl;
    private Boolean thumbnail;

    @ManyToOne
    private ProductEntity product;
}
