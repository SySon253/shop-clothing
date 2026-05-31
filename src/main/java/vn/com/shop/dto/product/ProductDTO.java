package vn.com.shop.dto.product;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProductDTO {
    private Long id;

    private String name;

    private String description;

    private String brand;

    private Long categoryId;

    private String categoryName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<ProductImageDTO> images;
}
