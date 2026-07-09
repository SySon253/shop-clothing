package vn.com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import vn.com.shop.entity.CategoryEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findById(Long id); // duy nhất thì dùng cái này
    List<CategoryEntity> findByName(String name);
    List<CategoryEntity> findByNameContainingIgnoreCase(String keyword);
    boolean existsByName(String name);
}
