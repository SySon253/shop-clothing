package vn.com.shop.mapper;

import org.springframework.stereotype.Component;
import vn.com.shop.dto.product.InventoryMovementResponseDTO;
import vn.com.shop.entity.InventoryMovementEntity;
import vn.com.shop.entity.ProductVariantEntity;

@Component
public class InventoryMovementMapper {

    public InventoryMovementResponseDTO entityToDto(
            InventoryMovementEntity entity){

        InventoryMovementResponseDTO dto =
                new InventoryMovementResponseDTO();

        dto.setId(entity.getId());

        dto.setMovementType(entity.getMovementType());

        dto.setQuantityBefore(entity.getQuantityBefore());

        dto.setQuantityChange(entity.getQuantityChange());

        dto.setQuantityAfter(entity.getQuantityAfter());

        dto.setReason(entity.getReason());

        dto.setReferenceCode(entity.getReferenceCode());

        dto.setCreatedDate(entity.getCreatedDate());
        ProductVariantEntity variant =
                entity.getVariant();

        dto.setSku(
                variant.getSku()
        );

        dto.setSize(
                variant.getSize()
        );

        dto.setColor(
                variant.getColor()
        );

        dto.setProductName(
                variant.getProduct().getName()
        );

        return dto;

    }

}