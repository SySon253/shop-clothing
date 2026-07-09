package vn.com.shop.service;

import vn.com.shop.dto.product.InventoryMovementResponseDTO;
import vn.com.shop.dto.product.InventoryMovementType;
import vn.com.shop.entity.ProductVariantEntity;

import java.util.List;

public interface IInventoryMovementService {
    void createMovement(

            ProductVariantEntity variant,

            InventoryMovementType type,

            Integer before,

            Integer change,

            Integer after,

            String reason,

            String reference

    );
    List<InventoryMovementResponseDTO> getHistory(Long variantId);
}
