package vn.com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.shop.entity.UserEntity;

// là interface được kế thừa từ JpaRespository

@Repository // để spring boot biết đây là interface repository và tự động tạo và quản lý bean
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
