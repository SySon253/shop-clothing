package vn.com.shop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.shop.dto.product.ProductVariantRequestDTO;
import vn.com.shop.dto.product.ProductVariantResponseDTO;
import vn.com.shop.dto.response.ResponsePage;
import vn.com.shop.service.IProductVariantService;

import java.util.List;

@RestController
@RequestMapping("/api/product-variants")
@RequiredArgsConstructor
public class ProductVariantController {
    private final IProductVariantService productVariantService;
    @PostMapping
    public ResponseEntity<ProductVariantResponseDTO> createVariant(
            @Valid @RequestBody ProductVariantRequestDTO requestDTO){
        return ResponseEntity.ok(productVariantService.createVariant(requestDTO));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductVariantResponseDTO> getVariant(@PathVariable Long id){
        return ResponseEntity.ok(productVariantService.getVariantById(id));
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductVariantResponseDTO>> getProductVariant(@PathVariable Long productId){
        return ResponseEntity.ok(productVariantService.getVariantsByProduct(productId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductVariantResponseDTO> updateVariant(@PathVariable Long id,@Valid @RequestBody ProductVariantRequestDTO requestDTO){
        ProductVariantResponseDTO productVariantResponseDTO = productVariantService.updateVariant(id, requestDTO);
        return ResponseEntity.ok(productVariantResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductVariantResponseDTO> deleteVariant(@PathVariable Long id){
        productVariantService.deleteVariant(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public ResponseEntity<ResponsePage<ProductVariantResponseDTO>> getAllVariants(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable) {

        return ResponseEntity.ok(
                productVariantService.getAllVariants(pageable)
        );
    }
}
