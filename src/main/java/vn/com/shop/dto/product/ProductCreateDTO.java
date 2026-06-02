package vn.com.shop.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCreateDTO {
    private String name;
    private String slug;
    private String description;
    private String brand;
    private Long categoryId;
}