package vn.com.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.shop.dto.product.ProductCreateDTO;
import vn.com.shop.dto.product.ProductResponseDTO;
import vn.com.shop.dto.product.ProductUpdateDTO;
import vn.com.shop.dto.request.ProductRequestFilter;
import vn.com.shop.dto.response.ResponsePage;
import vn.com.shop.entity.CategoryEntity;
import vn.com.shop.entity.ProductEntity;
import vn.com.shop.mapper.ProductMapper;
import vn.com.shop.repository.CategoryRepository;
import vn.com.shop.repository.ProductRepository;
import vn.com.shop.service.IProductService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductMapper productMapper;
    @Override
    public ResponsePage<ProductResponseDTO> getAllProduct(ProductRequestFilter requestFilter, Pageable pageable) {
        // Query database
        Page<ProductEntity> page = productRepository.findAllByDeletedFalse(pageable);

        List<ProductEntity> productEntities = page.getContent();

        // Convert Entity -> DTO
        List<ProductResponseDTO> productDTOs = new ArrayList<>();

        for (ProductEntity productEntity : productEntities) {
            ProductResponseDTO productDTO = productMapper.entityToDto(productEntity);
            productDTOs.add(productDTO);
        }

        // Set response
        ResponsePage<ProductResponseDTO> responsePage = new ResponsePage<>();

        responsePage.setContent(productDTOs);
        responsePage.setPageNumber(pageable.getPageNumber());
        responsePage.setPageSize(pageable.getPageSize());
        responsePage.setTotalPages(page.getTotalPages());
        responsePage.setTotalElements(page.getTotalElements());

        return responsePage;
    }

    @Override
    public ProductResponseDTO getProductByName(String name) {
        if(name == null || name.isEmpty()){
            throw new RuntimeException("name is required");
        }
        ProductEntity productEntity = productRepository.findByName(name).orElseThrow(() -> new RuntimeException("product not found"));
        return productMapper.entityToDto(productEntity);
    }


    @Override
    public ProductResponseDTO createProduct(ProductCreateDTO request) {
        CategoryEntity categoryEntity = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new RuntimeException("category not found"));
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(request.getName());
        productEntity.setDescription(request.getDescription());
//        productEntity.setSlug(request.getSlug());
        String slug = request.getName()
                .trim()
                .toLowerCase()
                .replace(" ", "-");
        if(productRepository.existsBySlug(slug)){
            throw new RuntimeException("Slug đã tồn tại.");
        }

        productEntity.setSlug(slug);
        productEntity.setDescription(request.getDescription());
        productEntity.setBrand(request.getBrand());
        productEntity.setCreatedBy(request.getCreateBy());
        productEntity.setLastModifiedBy(request.getLastModifiedBy());
        productEntity.setCategory(categoryEntity);
        productRepository.save(productEntity);
        return productMapper.entityToDto(productEntity);
    }

    @Override
    public ProductResponseDTO updateProduct(Long id, ProductUpdateDTO request) {
        ProductEntity productEntity = productRepository.findById(id).orElseThrow(() -> new RuntimeException("product not found"));
        if (request.getName() != null) {productEntity.setName(request.getName());}
        if (request.getDescription() != null) {productEntity.setDescription(request.getDescription());}
        if (request.getActive() != null) {productEntity.setActive(request.getActive());}
        if (request.getBrand() != null) {productEntity.setBrand(request.getBrand());}
        if(request.getCategoryId() != null){

            CategoryEntity category =
                    categoryRepository.findById(request.getCategoryId())
                            .orElseThrow(
                                    () -> new RuntimeException("Category not found")
                            );

            productEntity.setCategory(category);

        }
        productEntity = productRepository.save(productEntity);
        return productMapper.entityToDto(productEntity);
    }

//    @Override
//    public void deleteProduct(Long id) {
//        ProductEntity productEntity = productRepository.findById(id).orElseThrow(() -> new RuntimeException("product not found"));
//
//        productEntity.setDeleted(true);
//        productRepository.save(productEntity);
//    }
@Override
@Transactional
public void deleteProduct(Long id) {

    ProductEntity product =
            productRepository.findById(id)
                    .orElseThrow(() ->
                            new RuntimeException("product not found")
                    );


    product.setDeleted(true);


    product.getVariants()
            .forEach(variant ->
                    variant.setDeleted(true)
            );


    product.getImages()
            .forEach(image ->
                    image.setDeleted(true)
            );


}

    @Override
    public ProductResponseDTO getProductById(Long id) {
        ProductEntity product = productRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Không tìm thấy sản phẩm")
                );

        System.out.println("Images = " + product.getImages().size());
        return productMapper.entityToDto(product);
    }
}
