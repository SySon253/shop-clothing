package vn.com.shop.dto.product;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductUpdateDTO {
    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String name;
    private String description;
    @NotBlank(message = "Thương hiệu không được để trống")
    private String brand;
    private Boolean active;
    private Long categoryId;
}