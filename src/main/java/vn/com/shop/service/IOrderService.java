package vn.com.shop.service;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import vn.com.shop.dto.order.OrderCreateResponseDTO;
import vn.com.shop.dto.order.OrderRequestDTO;
import vn.com.shop.dto.order.OrderResponseDTO;
import vn.com.shop.dto.request.OrderRequestFilter;
import vn.com.shop.dto.response.ResponsePage;

import java.util.List;

public interface IOrderService {
    OrderCreateResponseDTO createOrder(
            OrderRequestDTO request
    );

    ResponsePage<OrderResponseDTO> getAllOrders(OrderRequestFilter orderFilterRequest, Pageable pageable);

    void completePayment(
            Long orderId
    );

    @Transactional(readOnly = true)
    List<OrderResponseDTO> getMyOrders();

}
