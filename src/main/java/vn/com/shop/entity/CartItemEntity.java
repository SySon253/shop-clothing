package vn.com.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "cart_item")
@Getter
@Setter
public class CartItemEntity extends BaseEntity {


    @ManyToOne
    @JoinColumn(
            name = "cart_id",
            nullable = false
    )
    private CartEntity cart;



    @ManyToOne
    @JoinColumn(
            name = "variant_id",
            nullable = false
    )
    private ProductVariantEntity variant;



    private Integer quantity;

}