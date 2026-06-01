package vn.com.shop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reviews")
public class ReviewEntity extends BaseEntity {

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private ProductEntity product;

    private Integer rating;
    private String comment;
}