package vn.com.shop.dto.product;

import lombok.Getter;
import lombok.Setter;
import vn.com.shop.dto.BaseResponseDTO;
import vn.com.shop.dto.category.CategoryResponseDTO;

import java.util.List;

@Getter
@Setter
public class ProductResponseDTO extends BaseResponseDTO {

    private String name;

    private String slug;

    private String description;

    private String brand;

    private Boolean active;

    private CategoryResponseDTO category;

    private List<ProductImageResponseDTO> images;

    private List<ProductVariantResponseDTO> variants;
}