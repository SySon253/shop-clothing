package vn.com.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.shop.dto.product.ProductImageRequestDTO;
import vn.com.shop.dto.product.ProductImageResponseDTO;
import vn.com.shop.service.IProductImageService;

import java.util.List;

@RestController
@RequestMapping("/api/product-images")
@RequiredArgsConstructor
public class ProductImageController {
    private final IProductImageService productImageService;

    @PostMapping
    public ResponseEntity<ProductImageResponseDTO> createImage(@RequestBody ProductImageRequestDTO productImageRequestDTO) {
        return ResponseEntity.ok(productImageService.createImage(productImageRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductImageResponseDTO> getImage(@PathVariable Long id) {
        return ResponseEntity.ok(productImageService.getImageById(id));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductImageResponseDTO>> getProductImages(@PathVariable Long productId) {
        return ResponseEntity.ok(productImageService.getImagesByProductId(productId));
    }
}
