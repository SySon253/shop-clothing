package vn.com.shop.service;


import org.springframework.data.domain.Pageable;
import vn.com.shop.dto.category.CategoryCreateDTO;
import vn.com.shop.dto.category.CategoryResponseDTO;
import vn.com.shop.dto.category.CategoryUpdateDTO;
import vn.com.shop.dto.request.CategoryRequestFilter;
import vn.com.shop.dto.response.ResponsePage;

import java.util.List;

public interface ICategoryService{
    ResponsePage<List<CategoryResponseDTO>> getAllCategories(CategoryRequestFilter requestFilter, Pageable pageable);
    List<CategoryResponseDTO> getCategoryByName(String name);
    CategoryResponseDTO createCategory(CategoryCreateDTO categoryCreateDTO);
    CategoryResponseDTO updateCategory(Long id, CategoryUpdateDTO categoryUpdateDTO);
    void deleteCategory(Long id);
}
