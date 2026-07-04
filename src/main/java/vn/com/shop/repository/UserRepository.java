package vn.com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.shop.entity.UserEntity;

import java.util.List;
import java.util.Optional;

// là interface được kế thừa từ JpaRespository

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("""
        SELECT u FROM UserEntity u 
        WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :username, '%'))
    """)
    List<UserEntity> searchByUsername(@Param("username") String username);
    Optional<UserEntity> findFirstByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
