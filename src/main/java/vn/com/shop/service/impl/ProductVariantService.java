package vn.com.shop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.shop.dto.product.ProductVariantRequestDTO;
import vn.com.shop.dto.product.ProductVariantResponseDTO;
import vn.com.shop.dto.response.ResponsePage;
import vn.com.shop.entity.ProductEntity;
import vn.com.shop.entity.ProductVariantEntity;
import vn.com.shop.mapper.ProductVariantMapper;
import vn.com.shop.repository.ProductRepository;
import vn.com.shop.repository.ProductVariantRepository;
import vn.com.shop.service.IProductVariantService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductVariantService implements IProductVariantService {
    private final ProductRepository productRepository;

    private final ProductVariantRepository productVariantRepository;

    private final ProductVariantMapper productVariantMapper;

    @Override
    public ProductVariantResponseDTO createVariant(
            ProductVariantRequestDTO requestDTO) {

        ProductEntity product =
                productRepository.findById(
                                requestDTO.getProductId())
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Product not found"));
        if(productVariantRepository
                .findBySkuAndDeletedFalse(requestDTO.getSku())
                .isPresent()){

            throw new RuntimeException("SKU đã tồn tại");

        }
        ProductVariantEntity variant = new ProductVariantEntity();

        variant.setProduct(product);

        variant.setSku(requestDTO.getSku());
        variant.setPrice(requestDTO.getPrice());
        variant.setDiscountPrice(requestDTO.getDiscountPrice());
        variant.setStock(requestDTO.getStock());
        variant.setReorderLevel(

                requestDTO.getReorderLevel() == null

                        ?

                        20

                        :

                        requestDTO.getReorderLevel()

        );
        variant.setReservedStock(
                requestDTO.getReservedStock() == null
                        ? 0
                        : requestDTO.getReservedStock()
        );
        variant.setSize(requestDTO.getSize());
        variant.setColor(requestDTO.getColor());

        productVariantRepository.save(variant);

        return productVariantMapper.entityToDto(variant);
    }

    @Override
    public ProductVariantResponseDTO getVariantById(
            Long id) {

        ProductVariantEntity variant =
                productVariantRepository.findByIdAndDeletedFalse(id)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Variant not found"));

        return productVariantMapper.entityToDto(
                variant);
    }

    @Override
    public List<ProductVariantResponseDTO>
    getVariantsByProduct(Long productId) {

        return productVariantRepository
                .findByProductIdAndDeletedFalse(productId)
                .stream()
                .map(productVariantMapper::entityToDto)
                .toList();
    }

    @Override
    public ProductVariantResponseDTO updateVariant(
            Long id,
            ProductVariantRequestDTO requestDTO) {

        ProductVariantEntity variant =
                productVariantRepository.findByIdAndDeletedFalse(id)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Variant not found"));

        variant.setPrice(requestDTO.getPrice());
        variant.setDiscountPrice(
                requestDTO.getDiscountPrice());
        variant.setStock(requestDTO.getStock());
        variant.setReservedStock(
                requestDTO.getReservedStock());
        variant.setSize(requestDTO.getSize());
        variant.setColor(requestDTO.getColor());
        variant.setReorderLevel(

                requestDTO.getReorderLevel()

        );

        productVariantRepository.save(variant);

        return productVariantMapper.entityToDto(
                variant);
    }

    @Override
    public void deleteVariant(Long id) {

        ProductVariantEntity variant =
                productVariantRepository.findById(id)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Variant not found"));

        variant.setDeleted(true);

        productVariantRepository.save(variant);
    }
    @Transactional
    public void decreaseStock(
            Long variantId,
            Integer quantity
    ){

        ProductVariantEntity variant =
                productVariantRepository
                        .findByIdAndDeletedFalse(variantId)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Variant không tồn tại")
                        );


        if(variant.getStock() < quantity){

            throw new RuntimeException(
                    "Không đủ tồn kho"
            );

        }


        variant.setStock(
                variant.getStock() - quantity
        );
        variant.setSold(

                variant.getSold()

                        + quantity

        );

        productVariantRepository.save(variant);

    }
    @Override
    public ResponsePage<ProductVariantResponseDTO> getAllVariants(Pageable pageable) {

        Page<ProductVariantEntity> page =
                productVariantRepository.findByDeletedFalse(pageable);

        List<ProductVariantResponseDTO> data =
                page.getContent()
                        .stream()
                        .map(productVariantMapper::entityToDto)
                        .toList();

        return ResponsePage.<ProductVariantResponseDTO>builder()
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .content(data)
                .build();
    }
}
