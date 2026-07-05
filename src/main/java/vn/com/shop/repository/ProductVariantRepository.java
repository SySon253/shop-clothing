package vn.com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.shop.entity.ProductVariantEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariantEntity, Long> {
    Optional<ProductVariantEntity>
    findBySkuAndDeletedFalse(String sku);
    List<ProductVariantEntity>
    findByProductIdAndDeletedFalse(Long productId);
    Optional<ProductVariantEntity>
    findByIdAndDeletedFalse(Long id);
}
