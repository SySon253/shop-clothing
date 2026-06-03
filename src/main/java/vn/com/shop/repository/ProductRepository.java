package vn.com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.shop.entity.ProductEntity;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity,Long> {
    Optional<ProductEntity> findByName(String name);
}
