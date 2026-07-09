package vn.com.shop.mapper;

import org.springframework.stereotype.Component;
import vn.com.shop.dto.category.CategoryResponseDTO;
import vn.com.shop.entity.CategoryEntity;

@Component
public class CategoryMapper {
    public CategoryResponseDTO entityToDto(CategoryEntity categoryEntity) {
        if (categoryEntity == null) {
            return null;
        }
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setId(categoryEntity.getId());
        categoryResponseDTO.setName(categoryEntity.getName());
        categoryResponseDTO.setCreatedBy(categoryEntity.getCreatedBy());
        categoryResponseDTO.setCreatedDate(categoryEntity.getCreatedDate());
        categoryResponseDTO.setLastModifiedBy(categoryEntity.getLastModifiedBy());
        categoryResponseDTO.setLastModifiedDate(categoryEntity.getLastModifiedDate());
        categoryResponseDTO.setSlug(categoryEntity.getSlug());
        return categoryResponseDTO;
    }
}
