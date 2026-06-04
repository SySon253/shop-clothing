package vn.com.shop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.com.shop.dto.product.ProductImageRequestDTO;
import vn.com.shop.dto.product.ProductImageResponseDTO;
import vn.com.shop.entity.ProductEntity;
import vn.com.shop.entity.ProductImageEntity;
import vn.com.shop.mapper.ProductImageMapper;
import vn.com.shop.repository.ProductImageRepository;
import vn.com.shop.repository.ProductRepository;
import vn.com.shop.service.IProductImageService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductImageService implements IProductImageService {
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductImageMapper productImageMapper;
    @Override
    public ProductImageResponseDTO createImage(ProductImageRequestDTO productImageRequestDTO) {
        ProductEntity productEntity = productRepository.findById(productImageRequestDTO.getProductId()).orElseThrow(
                () -> new RuntimeException("Image not found")
        );
        ProductImageEntity productImageEntity = new ProductImageEntity();
        productImageEntity.setProduct(productEntity);

        productImageEntity.setImageUrl(productImageRequestDTO.getImageUrl());
        productImageEntity.setThumbnail(productImageRequestDTO.getThumbnail());

        productImageEntity = productImageRepository.save(productImageEntity);

        return productImageMapper.entityToDto(productImageEntity);
    }

    @Override
    public ProductImageResponseDTO getImageById(Long id) {
        ProductImageEntity productImageEntity = productImageRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Image not found")
        );
        return productImageMapper.entityToDto(productImageEntity);
    }

    @Override
    public List<ProductImageResponseDTO> getImagesByProductId(Long productId) {
        return productImageRepository.findByProductId(productId)
                .stream()
                .map(productImageMapper::entityToDto)
                .toList();
    }

    @Override
    public ProductImageResponseDTO updateImage(Long id, ProductImageRequestDTO productImageRequestDTO) {
        ProductImageEntity productImageEntity = productImageRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Image not found")
        );
        productImageEntity.setImageUrl(productImageRequestDTO.getImageUrl());
        productImageEntity.setThumbnail(productImageRequestDTO.getThumbnail());

        productImageEntity = productImageRepository.save(productImageEntity);
        return productImageMapper.entityToDto(productImageEntity);
    }

    @Override
    public void deleteImage(Long id) {
        ProductImageEntity productImageEntity = productImageRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Image not found")
        );
        productImageEntity.setDeleted(true);
        productImageRepository.save(productImageEntity);
    }
}
