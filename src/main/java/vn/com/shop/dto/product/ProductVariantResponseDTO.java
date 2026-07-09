package vn.com.shop.dto.product;

import lombok.Getter;
import lombok.Setter;
import vn.com.shop.dto.BaseResponseDTO;

import java.math.BigDecimal;


@Getter
@Setter
public class ProductVariantResponseDTO extends BaseResponseDTO {

    private String productName;

    private String sku;

    private BigDecimal price;

    private BigDecimal discountPrice;

    // On Hand
    private Integer stock;

    // Reserved
    private Integer reservedStock;

    // Available
    private Integer available;

    // Lifetime Sold
    private Integer sold;

    private String size;

    private String color;

    private String stockStatus;

    private Boolean needRestock;

    private Integer reorderLevel;
    private Integer availableStock;

    private Double inventoryValue;

}