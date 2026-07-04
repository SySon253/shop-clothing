package vn.com.shop.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductRequestDTO {
    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String name;
    private String slug;
    private String description;
    private String brand;
    private Boolean active;
    @NotNull(message = "Danh mục không được để trống")
    private Long categoryId;
}
