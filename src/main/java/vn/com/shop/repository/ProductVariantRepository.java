package vn.com.shop.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
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
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
    SELECT v
    FROM ProductVariantEntity v
    WHERE v.id = :id
""")
    Optional<ProductVariantEntity> findByIdForUpdate(Long id);
    List<ProductVariantEntity> findByDeletedFalse();
    Page<ProductVariantEntity> findByDeletedFalse(Pageable pageable);
}
