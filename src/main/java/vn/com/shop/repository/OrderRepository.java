package vn.com.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.com.shop.entity.OrderEntity;
import vn.com.shop.entity.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long>, JpaSpecificationExecutor<OrderEntity> {
    List<OrderEntity> findByUserId(Long userId);
    Page<OrderEntity> findByStatus(
            OrderStatus status,
            Pageable pageable
    );
    Page<OrderEntity> findAllByOrderByCreatedDateDesc(
            Pageable pageable
    );
    List<OrderEntity> findByStatusAndCreatedDateBefore(
            OrderStatus status,
            LocalDateTime time
    );
}
