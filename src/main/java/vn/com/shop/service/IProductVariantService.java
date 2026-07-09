package vn.com.shop.service;

import org.springframework.data.domain.Pageable;
import vn.com.shop.dto.product.ProductVariantRequestDTO;
import vn.com.shop.dto.product.ProductVariantResponseDTO;
import vn.com.shop.dto.response.ResponsePage;

import java.util.List;

public interface IProductVariantService {
    ProductVariantResponseDTO createVariant(ProductVariantRequestDTO productVariantRequestDTO);
    ProductVariantResponseDTO getVariantById(Long id);
    List<ProductVariantResponseDTO> getVariantsByProduct(Long productId);
    ProductVariantResponseDTO updateVariant(Long id, ProductVariantRequestDTO requestDTO);
    void deleteVariant(Long id);
    ResponsePage<ProductVariantResponseDTO> getAllVariants(Pageable pageable);
}
