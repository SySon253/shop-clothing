package vn.com.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "order_item")
@Getter
@Setter
public class OrderItemEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "variant_id")
    private ProductVariantEntity productVariant;

    private String productName;
    private String sku;
    private String size;
    private String color;
    private BigDecimal price;
    private Integer quantity;
    private Long cartItemId;
}
