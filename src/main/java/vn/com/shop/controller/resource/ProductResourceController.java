package vn.com.shop.controller.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.shop.dto.product.ProductResponseDTO;
import vn.com.shop.dto.request.ProductRequestFilter;
import vn.com.shop.dto.response.ResponsePage;
import vn.com.shop.service.IProductService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductResourceController {
    private final IProductService productService;
    @PostMapping("/all-product")
    public ResponseEntity<ResponsePage<List<ProductResponseDTO>>> getAllProduct(
            @RequestBody ProductRequestFilter requestFilter,
            Pageable pageable) {
        return ResponseEntity.ok(productService.getAllProduct(requestFilter, pageable));
    }

    @GetMapping("/{name}")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable String name) {
        return ResponseEntity.ok(productService.getProductByName(name));
    }
}
