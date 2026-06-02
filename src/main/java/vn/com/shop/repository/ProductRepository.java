package vn.com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.shop.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity,Long> {
}
