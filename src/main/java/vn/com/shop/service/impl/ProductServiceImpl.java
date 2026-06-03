package vn.com.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.com.shop.dto.product.ProductCreateDTO;
import vn.com.shop.dto.product.ProductResponseDTO;
import vn.com.shop.dto.product.ProductUpdateDTO;
import vn.com.shop.dto.request.ProductRequestFilter;
import vn.com.shop.dto.response.ResponsePage;
import vn.com.shop.entity.ProductEntity;
import vn.com.shop.mapper.ProductMapper;
import vn.com.shop.repository.ProductRepository;
import vn.com.shop.service.IProductService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;
@Override
public ResponsePage<List<ProductResponseDTO>> getAllProduct(ProductRequestFilter requestFilter, Pageable pageable) {

    // Query database
    Page<ProductEntity> page = productRepository.findAll(pageable);

    List<ProductEntity> productEntities = page.getContent();

    // Convert Entity -> DTO
    List<ProductResponseDTO> productDTOs = new ArrayList<>();

    for (ProductEntity productEntity : productEntities) {
        ProductResponseDTO productDTO = productMapper.entityToDto(productEntity);
        productDTOs.add(productDTO);
    }

    // Set response
    ResponsePage<List<ProductResponseDTO>> responsePage = new ResponsePage<>();

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
        return null;
    }

    @Override
    public ProductResponseDTO updateProduct(Long id, ProductUpdateDTO request) {
        return null;
    }

    @Override
    public void deleteProduct(Long id) {

    }
}
