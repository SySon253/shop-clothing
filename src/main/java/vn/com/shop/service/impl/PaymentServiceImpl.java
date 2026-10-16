package vn.com.shop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.shop.entity.*;
import vn.com.shop.repository.CartRepository;
import vn.com.shop.repository.OrderRepository;
import vn.com.shop.repository.PaymentRepository;
import vn.com.shop.repository.ProductVariantRepository;
import vn.com.shop.service.IInventoryService;
import vn.com.shop.service.IPaymentService;
import vn.com.shop.service.IVNPayService;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements IPaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ProductVariantRepository productVariantRepository;
    private final IInventoryService iInventoryService;
    private final IVNPayService vnPayService;

    @Override
    public String handleVNPayCallback(Map<String, String> params) {
        // 1. Verify chữ ký VNPay
        boolean valid = vnPayService.verifyPayment(params);
        if (!valid) {
            return "INVALID_SIGNATURE";
        }

        // 2. Lấy orderId từ txnRef
        String orderId = params.get("vnp_TxnRef");
        PaymentEntity payment = paymentRepository.findByOrderId(Long.valueOf(orderId))
                .orElseThrow(() -> new RuntimeException("Không tìm thấy payment"));

        // 3. Kiểm tra trạng thái đơn hàng và kết quả VNPay
        OrderEntity order = payment.getOrder();
        if (OrderStatus.PAID.equals(order.getStatus())) {
            return "ALREADY_PAID";
        }

        String responseCode = params.get("vnp_ResponseCode");

        if ("00".equals(responseCode)) {
            // Thanh toán thành công
            payment.setPaymentStatus("SUCCESS");
            payment.setTransactionId(params.get("vnp_TransactionNo"));

            iInventoryService.deductStock(order);
            order.setStatus(OrderStatus.PAID);
            orderRepository.save(order);

            // Xóa sản phẩm đã mua khỏi giỏ hàng
            CartEntity cart = cartRepository.findByUserId(order.getUser().getId()).orElse(null);
            if (cart != null) {
                Set<Long> cartItemIds = order.getItems().stream()
                        .map(OrderItemEntity::getCartItemId)
                        .collect(Collectors.toSet());

                cart.getItems().removeIf(item -> cartItemIds.contains(item.getId()));
                cartRepository.save(cart);
            }
        } else {
            // Thanh toán thất bại
            payment.setPaymentStatus("FAILED");
            order.setStatus(OrderStatus.CANCELLED);
        }

        paymentRepository.save(payment);
        orderRepository.save(order);

        return responseCode;
    }
}