package vn.com.shop.controller.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.shop.dto.category.CategoryCreateDTO;
import vn.com.shop.dto.category.CategoryResponseDTO;
import vn.com.shop.dto.category.CategoryUpdateDTO;
import vn.com.shop.dto.request.CategoryRequestFilter;
import vn.com.shop.dto.response.ResponsePage;
import vn.com.shop.service.ICategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryResourceController {
    private final ICategoryService categoryService;

    @PostMapping("/all-category")
    public ResponseEntity<ResponsePage<List<CategoryResponseDTO>>> getAllCategory(@RequestBody(required = false) CategoryRequestFilter requestFilter, Pageable pageable) {
        if (requestFilter == null) {
            requestFilter = new CategoryRequestFilter();
        }
        return ResponseEntity.ok(categoryService.getAllCategories(requestFilter, pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategory(@RequestParam String name) {
        return ResponseEntity.ok(categoryService.getCategoryByName(name));
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createdCategory(@RequestBody CategoryCreateDTO categoryCreateDTO){
        CategoryResponseDTO categoryResponseDTO = categoryService.createCategory(categoryCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryResponseDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryUpdateDTO categoryUpdateDTO){
        CategoryResponseDTO categoryResponseDTO = categoryService.updateCategory(id, categoryUpdateDTO);
        return ResponseEntity.ok(categoryResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
