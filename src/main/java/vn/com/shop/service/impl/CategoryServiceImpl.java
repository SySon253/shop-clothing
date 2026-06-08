package vn.com.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.com.shop.dto.category.CategoryCreateDTO;
import vn.com.shop.dto.category.CategoryResponseDTO;
import vn.com.shop.dto.category.CategoryUpdateDTO;
import vn.com.shop.dto.request.CategoryRequestFilter;
import vn.com.shop.dto.response.ResponsePage;
import vn.com.shop.entity.CategoryEntity;
import vn.com.shop.mapper.CategoryMapper;
import vn.com.shop.repository.CategoryRepository;
import vn.com.shop.service.ICategoryService;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ResponsePage<List<CategoryResponseDTO>> getAllCategories(CategoryRequestFilter requestFilter, Pageable pageable) {
        Page<CategoryEntity> page = categoryRepository.findAll(pageable);
        List<CategoryEntity> categoryEntities = page.getContent();
        List<CategoryResponseDTO> categoryResponseDTOS = new ArrayList<>();
        for (CategoryEntity categoryEntity : categoryEntities) {
            CategoryResponseDTO categoryResponseDTO = categoryMapper.entityToDto(categoryEntity);
            categoryResponseDTOS.add(categoryResponseDTO);
        }
        ResponsePage<List<CategoryResponseDTO>> responsePage = new ResponsePage<>();
        responsePage.setContent(categoryResponseDTOS);
        responsePage.setPageNumber(pageable.getPageNumber());
        responsePage.setPageSize(pageable.getPageSize());
        responsePage.setTotalElements(page.getTotalElements());
        responsePage.setTotalPages(page.getTotalPages());

        return responsePage;
    }

    @Override
    public List<CategoryResponseDTO> getCategoryByName(String keyword) {
//        if (name == null || name.isEmpty()){
//            throw new IllegalArgumentException("Category name cannot be null or empty");
//        }
        List<CategoryEntity> categoryEntities = categoryRepository.findByNameContainingIgnoreCase(keyword);
        return categoryEntities.stream().map(categoryMapper::entityToDto).toList();
    }

    @Override
    public CategoryResponseDTO createCategory(CategoryCreateDTO categoryCreateDTO) {
        if (categoryRepository.existsByName(categoryCreateDTO.getName())){
            throw new RuntimeException("Category name already exist");
        }
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(categoryCreateDTO.getName());
        categoryEntity.setSlug(categoryCreateDTO.getSlug());
        if(categoryCreateDTO.getParentId() != null){
            CategoryEntity parent = categoryRepository.findById(categoryCreateDTO.getParentId()).orElseThrow(() -> new RuntimeException("Parent category not found"));
            categoryEntity.setParent(parent);
        }
        categoryEntity = categoryRepository.save(categoryEntity);
        return categoryMapper.entityToDto(categoryEntity);
    }

    @Override
    public CategoryResponseDTO updateCategory(Long id, CategoryUpdateDTO categoryUpdateDTO) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        if (categoryUpdateDTO.getName() != null) { categoryEntity.setName(categoryUpdateDTO.getName());}
        if (categoryUpdateDTO.getSlug() != null) { categoryEntity.setSlug(categoryUpdateDTO.getSlug());}
        if (categoryUpdateDTO.getParentId() != null) {
            CategoryEntity parent = categoryRepository.findById(categoryUpdateDTO.getParentId()).orElseThrow(() -> new RuntimeException("Parent category not found"));
            categoryEntity.setParent(parent);
        }
        if (categoryUpdateDTO.getLastModifiedBy() != null) { categoryEntity.setLastModifiedBy(categoryUpdateDTO.getLastModifiedBy());}
        if (categoryUpdateDTO.getLastModifiedDate() != null) { categoryEntity.setLastModifiedDate(categoryUpdateDTO.getLastModifiedDate());}
        categoryEntity = categoryRepository.save(categoryEntity);
        return categoryMapper.entityToDto(categoryEntity);
    }

    @Override
    public void deleteCategory(Long id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        categoryEntity.setDeleted(true);
        categoryRepository.save(categoryEntity);
    }
}
