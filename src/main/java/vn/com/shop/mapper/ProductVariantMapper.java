package vn.com.shop.mapper;

import org.springframework.stereotype.Component;
import vn.com.shop.dto.product.ProductVariantResponseDTO;
import vn.com.shop.entity.ProductVariantEntity;

import java.util.List;
import java.util.Set;

@Component
public class ProductVariantMapper {
    public ProductVariantResponseDTO entityToDto(ProductVariantEntity productVariantEntity) {
        if(productVariantEntity == null) {return null;}
        ProductVariantResponseDTO productVariantResponseDTO = new ProductVariantResponseDTO();
        productVariantResponseDTO.setId(productVariantEntity.getId());
        productVariantResponseDTO.setSku(productVariantEntity.getSku());
        productVariantResponseDTO.setPrice(productVariantEntity.getPrice());
        productVariantResponseDTO.setDiscountPrice(productVariantEntity.getDiscountPrice());
        productVariantResponseDTO.setStock(productVariantEntity.getStock());
        productVariantResponseDTO.setReservedStock(productVariantEntity.getReservedStock());
        productVariantResponseDTO.setSize(productVariantEntity.getSize());
        productVariantResponseDTO.setColor(productVariantEntity.getColor());
        productVariantResponseDTO.setCreatedBy(productVariantEntity.getCreatedBy());
        productVariantResponseDTO.setCreatedDate(productVariantEntity.getCreatedDate());
        productVariantResponseDTO.setLastModifiedBy(productVariantEntity.getLastModifiedBy());
        productVariantResponseDTO.setLastModifiedDate(productVariantEntity.getLastModifiedDate());
        return productVariantResponseDTO;
    }
    public List<ProductVariantResponseDTO> entityToDto(Set<ProductVariantEntity> productVariantEntityList) {
        if(productVariantEntityList == null) {return null;}
        return productVariantEntityList.stream().map(this::entityToDto).toList();
    }
}
