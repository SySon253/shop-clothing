package vn.com.shop.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class UserReviewDTO {
    private Long id;

    private Long userId;

    private Long orderLineId;

    private Integer ratingValue;

    private String comment;

    private LocalDateTime createdAt;
}
