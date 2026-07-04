package vn.com.shop.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import vn.com.shop.entity.OrderEntity;
import vn.com.shop.entity.OrderStatus;
import vn.com.shop.entity.PaymentEntity;
import vn.com.shop.repository.OrderRepository;
import vn.com.shop.repository.PaymentRepository;
import vn.com.shop.repository.ProductVariantRepository;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderScheduler {

    /**
     * Sau bao lâu thì đơn chưa thanh toán sẽ bị hủy
     */
    private static final long EXPIRED_MINUTES = 10;

    private final OrderRepository orderRepository;

    private final PaymentRepository paymentRepository;

    private final ProductVariantRepository productVariantRepository;

    /**
     * Mỗi 1 phút kiểm tra một lần
     */
    @Scheduled(fixedDelay = 60000)
    @Transactional
    public void cancelExpiredOrders() {

        LocalDateTime expiredTime =
                LocalDateTime.now()
                        .minusMinutes(EXPIRED_MINUTES);

        List<OrderEntity> orders =
                orderRepository
                        .findByStatusAndCreatedDateBefore(
                                OrderStatus.WAITING_PAYMENT,
                                expiredTime
                        );

        if (orders.isEmpty()) {

            log.info("Không có đơn WAITING_PAYMENT hết hạn.");

            return;
        }

        log.info("Tìm thấy {} đơn hết hạn.", orders.size());

        for (OrderEntity order : orders) {

            try {

                PaymentEntity payment =
                        order.getPayment();

                /*
                 * Nếu payment không còn PENDING
                 * nghĩa là callback VNPay đã xử lý
                 */

                if (payment == null) {

                    continue;

                }

                if (!"PENDING".equals(payment.getPaymentStatus())) {

                    continue;

                }

                /*
                 * Hủy đơn
                 */

                order.setStatus(
                        OrderStatus.CANCELLED
                );

                /*
                 * Payment hết hạn
                 */

                payment.setPaymentStatus(
                        "EXPIRED"
                );

                /*
                 * Nếu sau này giữ tồn kho khi tạo đơn
                 * thì chỉ cần mở comment đoạn dưới.
                 */

                /*
                for (OrderItemEntity item : order.getItems()) {

                    ProductVariantEntity variant =
                            item.getProductVariant();

                    variant.setStock(
                            variant.getStock()
                                    + item.getQuantity()
                    );

                    productVariantRepository.save(variant);

                }
                */

                paymentRepository.save(payment);

                orderRepository.save(order);

                log.info(
                        "Đã hủy đơn {}",
                        order.getOrderCode()
                );

            }
            catch (Exception e) {

                log.error(
                        "Lỗi khi xử lý đơn {}",
                        order.getId(),
                        e
                );

            }

        }

    }

}