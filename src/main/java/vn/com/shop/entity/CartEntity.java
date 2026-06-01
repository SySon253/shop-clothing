package vn.com.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "cart")
@Getter
@Setter
public class CartEntity extends BaseEntity {
    @OneToOne
    private UserEntity user;

    @OneToMany(mappedBy = "cart")
    private Set<CartItemEntity> items;
}
