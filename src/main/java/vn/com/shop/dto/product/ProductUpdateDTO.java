package vn.com.shop.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductUpdateDTO {
    private String name;
    private String description;
    private String brand;
    private Boolean active;
}