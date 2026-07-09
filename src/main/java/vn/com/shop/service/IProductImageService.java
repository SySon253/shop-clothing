package vn.com.shop.service;

import vn.com.shop.dto.product.ProductImageRequestDTO;
import vn.com.shop.dto.product.ProductImageResponseDTO;

import java.util.List;

public interface IProductImageService {
    ProductImageResponseDTO createImage(ProductImageRequestDTO productImageRequestDTO);
    ProductImageResponseDTO getImageById(Long id);
    List<ProductImageResponseDTO> getImagesByProductId(Long productId);
    ProductImageResponseDTO updateImage(Long id, ProductImageRequestDTO productImageRequestDTO);
    void deleteImage(Long id);
}
