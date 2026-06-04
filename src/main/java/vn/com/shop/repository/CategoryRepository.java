package vn.com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.shop.entity.CategoryEntity;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findById(Long id);
}
