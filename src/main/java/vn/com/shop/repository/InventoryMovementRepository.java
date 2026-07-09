package vn.com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.shop.entity.InventoryMovementEntity;

import java.util.List;

@Repository
public interface InventoryMovementRepository extends JpaRepository<InventoryMovementEntity, Long> {
    List<InventoryMovementEntity> findByVariantIdOrderByCreatedDateDesc(Long variantId);

}