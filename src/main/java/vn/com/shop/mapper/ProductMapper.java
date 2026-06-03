package vn.com.shop.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.com.shop.dto.product.ProductResponseDTO;
import vn.com.shop.entity.ProductEntity;

@Component
@RequiredArgsConstructor
public class ProductMapper {
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductImageMapper productImageMapper;

    @Autowired
    private ProductVariantMapper productVariantMapper;
    public ProductResponseDTO entityToDto(ProductEntity productEntity){
        if(productEntity == null){
            return null;
        }
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId(productEntity.getId());
        productResponseDTO.setName(productEntity.getName());
        productResponseDTO.setDescription(productEntity.getDescription());
        productResponseDTO.setCreatedBy(productEntity.getCreatedBy());
        productResponseDTO.setCreatedDate(productEntity.getCreatedDate());
        productResponseDTO.setLastModifiedBy(productEntity.getLastModifiedBy());
        productResponseDTO.setLastModifiedDate(productEntity.getLastModifiedDate());
        productResponseDTO.setSlug(productEntity.getSlug());
        productResponseDTO.setBrand(productEntity.getBrand());
        productResponseDTO.setActive(productEntity.getActive());
        productResponseDTO.setCategory(categoryMapper.entityToDto(productEntity.getCategory()));
        productResponseDTO.setImages(productImageMapper.entityToDto(productEntity.getImages()));
        productResponseDTO.setVariants(productVariantMapper.entityToDto(productEntity.getVariants()));
        return productResponseDTO;
    }
    public ProductEntity dtoToEntity(ProductResponseDTO productResponseDTO){
        if(productResponseDTO == null){
            return null;
        }
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(productResponseDTO.getId());
        productEntity.setName(productResponseDTO.getName());
        productEntity.setDescription(productResponseDTO.getDescription());
        return productEntity;
    }
}
