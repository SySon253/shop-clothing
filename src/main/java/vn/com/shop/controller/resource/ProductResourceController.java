package vn.com.shop.controller.resource;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.shop.dto.product.ProductCreateDTO;
import vn.com.shop.dto.product.ProductRequestDTO;
import vn.com.shop.dto.product.ProductResponseDTO;
import vn.com.shop.dto.product.ProductUpdateDTO;
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
    public ResponseEntity<ResponsePage<ProductResponseDTO>> getAllProduct(
            @RequestBody ProductRequestFilter requestFilter,
            Pageable pageable) {
        return ResponseEntity.ok(productService.getAllProduct(requestFilter, pageable));
    }

    @GetMapping("/{name}")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable String name) {
        return ResponseEntity.ok(productService.getProductByName(name));
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductCreateDTO productCreateDTO){
        ProductResponseDTO productResponseDTO = productService.createProduct(productCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponseDTO);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(
            @PathVariable Long id){

        return ResponseEntity.ok(
                productService.getProductById(id)
        );
    }

    @PatchMapping("/id/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id,@Valid @RequestBody ProductUpdateDTO productUpdateDTO) {
        ProductResponseDTO productResponseDTO = productService.updateProduct(id, productUpdateDTO);
        return ResponseEntity.ok(productResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
