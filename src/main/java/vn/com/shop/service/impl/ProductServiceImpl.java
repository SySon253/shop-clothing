package vn.com.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.shop.dto.product.ProductDTO;
import vn.com.shop.entity.ProductEntity;
import vn.com.shop.repository.ProductRepository;
import vn.com.shop.service.IProductService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ProductRepository productRepository;
    @Override
    public List<ProductDTO> getAllProduct() {
//        List<ProductEntity> products = productRepository.findAll();
//        List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
//        for (ProductEntity productEntity : products) {
//            ProductDTO productDTO = new ProductDTO();
//            productDTO.setId(productEntity.getProductID());
//            productDTO.setName(productEntity.getProductName());
//            productDTO.setDescription(productEntity.getDescription());
//            productDTO.setBrand(productEntity.getBrand());
//            if (productEntity.getCreatedDate() != null){
//                productDTO.setCreatedAt(productEntity.getCreatedDate().toLocalDate().atStartOfDay());
//            }
//        }
        return List.of();
    }

    @Override
    public ProductDTO findById(Long id) {
        return null;
    }

    @Override
    public ProductDTO updateProduct(ProductDTO product) {
        return null;
    }

    @Override
    public ProductDTO addProduct(ProductDTO product) {
        return null;
    }

    @Override
    public void deleteProduct(ProductDTO product) {

    }
}
