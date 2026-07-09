package vn.com.shop.dto.review;

import lombok.Getter;
import lombok.Setter;
import vn.com.shop.dto.BaseResponseDTO;

@Getter
@Setter
public class ReviewResponseDTO extends BaseResponseDTO {
    private Long userId;
    private String username;
    private Integer rating;
    private String comment;
}