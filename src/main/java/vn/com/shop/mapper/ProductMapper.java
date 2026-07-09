package vn.com.shop.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.com.shop.dto.product.ProductImageResponseDTO;
import vn.com.shop.dto.product.ProductResponseDTO;
import vn.com.shop.dto.product.ProductVariantResponseDTO;
import vn.com.shop.entity.CategoryEntity;
import vn.com.shop.entity.ProductEntity;

import java.util.List;

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
        System.out.println("Entity images = " + productEntity.getImages().size());
        List<ProductImageResponseDTO> images =
                productEntity.getImages()
                        .stream()
                        .filter(image -> !image.getDeleted())
                        .map(productImageMapper::entityToDto)
                        .toList();
        System.out.println("DTO images = " + images.size());
        productResponseDTO.setImages(images);
        List<ProductVariantResponseDTO> variants =
                productEntity.getVariants()
                        .stream()
                        .filter(variant -> !variant.getDeleted())
                        .map(productVariantMapper::entityToDto)
                        .toList();

        productResponseDTO.setVariants(variants);
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
        productEntity.setSlug(productResponseDTO.getSlug());
        productEntity.setBrand(productResponseDTO.getBrand());
        productEntity.setActive(true);

        return productEntity;
    }


}
