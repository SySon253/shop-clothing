package vn.com.shop.dto.product;

import lombok.Data;

@Data
public class ProductRequestDTO {
    private String name;
    private String slug;
    private String description;
    private String brand;
    private Boolean active;
    private Long categoryId;
}
