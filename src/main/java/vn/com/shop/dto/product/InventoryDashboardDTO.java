package vn.com.shop.dto.product;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class InventoryDashboardDTO {

    private Long totalVariants;

    private Long lowStock;

    private Long outOfStock;

    private BigDecimal inventoryValue;

}