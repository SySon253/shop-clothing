package vn.com.shop.mapper;

import org.springframework.stereotype.Component;
import vn.com.shop.dto.product.ProductImageResponseDTO;
import vn.com.shop.entity.ProductImageEntity;

import java.util.List;
import java.util.Set;

@Component
public class ProductImageMapper {
    public ProductImageResponseDTO entityToDto(ProductImageEntity productImageEntity){
        if(productImageEntity == null) {return null;}
        ProductImageResponseDTO productImageResponseDTO = new ProductImageResponseDTO();
        productImageResponseDTO.setId(productImageEntity.getId());
        productImageResponseDTO.setImageUrl(productImageEntity.getImageUrl());
        productImageResponseDTO.setThumbnail(productImageEntity.getThumbnail());
        productImageResponseDTO.setCreatedBy(productImageEntity.getCreatedBy());
        productImageResponseDTO.setCreatedDate(productImageEntity.getCreatedDate());
        productImageResponseDTO.setLastModifiedBy(productImageEntity.getLastModifiedBy());
        productImageResponseDTO.setLastModifiedDate(productImageEntity.getLastModifiedDate());
        return productImageResponseDTO;
    }
    public List<ProductImageResponseDTO> entityToDto(Set<ProductImageEntity> productImageEntityList){
        if(productImageEntityList == null) {return null;}

        return productImageEntityList.stream().map(this::entityToDto).toList();
    }
}
