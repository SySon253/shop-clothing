package vn.com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.shop.entity.PaymentEntity;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity,Long> {


    Optional<PaymentEntity> findByOrderId(Long orderId);


}
