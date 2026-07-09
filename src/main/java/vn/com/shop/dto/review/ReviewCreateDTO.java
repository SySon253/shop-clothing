package vn.com.shop.dto.review;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewCreateDTO {
    private Long productId;
    private Integer rating;
    private String comment;
}