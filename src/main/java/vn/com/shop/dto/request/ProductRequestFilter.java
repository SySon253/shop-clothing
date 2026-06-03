package vn.com.shop.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequestFilter {
    private String keyword;
    private Long categoryId;
    private String brand;
    private String color;
    private String sizeName;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Boolean active;
    private Boolean inStock;
    private Boolean hasDiscount;
    private Integer page = 0;
    private Integer size = 12;
    private String sortBy = "createdDate";
    private String sortDir = "desc";
}
