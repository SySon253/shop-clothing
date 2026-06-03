package vn.com.shop.service;



import org.springframework.data.domain.Pageable;
import vn.com.shop.dto.product.ProductCreateDTO;
import vn.com.shop.dto.product.ProductResponseDTO;
import vn.com.shop.dto.product.ProductUpdateDTO;
import vn.com.shop.dto.request.ProductRequestFilter;
import vn.com.shop.dto.response.ResponsePage;

import java.util.List;

public interface IProductService {
    ResponsePage<List<ProductResponseDTO>> getAllProduct(ProductRequestFilter requestFilter, Pageable pageable);
    ProductResponseDTO getProductByName(String name);
    ProductResponseDTO createProduct(ProductCreateDTO request);
    ProductResponseDTO updateProduct(Long id, ProductUpdateDTO request);
    void deleteProduct(Long id);
}
