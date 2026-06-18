package vn.com.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.com.shop.entity.ProductEntity;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity,Long> {
    Optional<ProductEntity> findByName(String name);
    Page<ProductEntity> findAllByDeletedFalse(Pageable pageable);
}
