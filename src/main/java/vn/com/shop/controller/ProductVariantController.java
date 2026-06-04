package vn.com.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.shop.dto.product.ProductVariantRequestDTO;
import vn.com.shop.dto.product.ProductVariantResponseDTO;
import vn.com.shop.service.IProductVariantService;

import java.util.List;

@RestController
@RequestMapping("/api/product-variants")
@RequiredArgsConstructor
public class ProductVariantController {
    private final IProductVariantService productVariantService;
    @PostMapping
    public ResponseEntity<ProductVariantResponseDTO> createVariant(
            @RequestBody ProductVariantRequestDTO requestDTO){
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
}
