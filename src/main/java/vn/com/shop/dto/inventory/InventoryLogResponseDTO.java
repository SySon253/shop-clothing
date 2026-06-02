package vn.com.shop.dto.inventory;

import lombok.Getter;
import lombok.Setter;
import vn.com.shop.dto.BaseResponseDTO;

@Getter
@Setter
public class InventoryLogResponseDTO extends BaseResponseDTO {
    private String sku;
    private String action;
    private Integer quantity;
    private String note;
}