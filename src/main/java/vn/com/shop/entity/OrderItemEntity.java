package vn.com.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_item")
@Getter
@Setter
public class OrderItemEntity extends BaseEntity {
    @ManyToOne
    private OrderEntity order;

    private String productName;
    private String sku;
    private String size;
    private String color;
    private Double price;
    private Integer quantity;
}
