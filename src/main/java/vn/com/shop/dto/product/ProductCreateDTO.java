package vn.com.shop.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCreateDTO {
    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String name;
    private String slug;
    private String description;
    @NotBlank(message = "Thương hiệu không được để trống")
    private String brand;
    @NotNull(message = "Danh mục không được để trống")
    private Long categoryId;
    private String createBy;
    private String lastModifiedBy;
}