package vn.com.shop.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductVariantRequestDTO {

    private Long productId;

    @NotBlank(message = "SKU không được để trống")
    private String sku;

    @NotNull(message = "Giá không được để trống")
    @Positive(message = "Giá phải lớn hơn 0")
    private BigDecimal price;

    private BigDecimal discountPrice;

    @NotNull(message = "Tồn kho không được để trống")
    @Min(value = 0, message = "Tồn kho phải lớn hơn hoặc bằng 0")
    private Integer stock;

    private Integer reservedStock;

    private String size;

    private String color;
    private Integer reorderLevel;
}