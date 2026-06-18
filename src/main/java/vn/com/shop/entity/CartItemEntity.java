package vn.com.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "cart_item")
@Getter
@Setter
public class CartItemEntity extends BaseEntity{
    @ManyToOne
    private CartEntity cart;

    @ManyToOne
    private ProductVariantEntity variant;

    private Integer quantity;
}
