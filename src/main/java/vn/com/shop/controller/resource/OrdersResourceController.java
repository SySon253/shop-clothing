package vn.com.shop.controller.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.shop.dto.order.OrderCreateResponseDTO;
import vn.com.shop.dto.order.OrderRequestDTO;
import vn.com.shop.dto.order.OrderResponseDTO;
import vn.com.shop.dto.request.OrderRequestFilter;
import vn.com.shop.dto.response.ResponsePage;
import vn.com.shop.entity.OrderStatus;
import vn.com.shop.service.IOrderService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrdersResourceController {
    private final IOrderService orderService;
    @PostMapping("/orders")
    public ResponseEntity<OrderCreateResponseDTO> createOrder(
            @RequestBody OrderRequestDTO request
    ){
        System.out.println("===== CREATE ORDER CONTROLLER =====");
        return ResponseEntity.ok(orderService.createOrder(request));
    }
    @GetMapping("/admin/orders")
    public ResponseEntity<ResponsePage<OrderResponseDTO>> getAllOrders(
            @RequestParam(required = false) String keyword,

            @RequestParam(required = false)
            OrderStatus status,

            Pageable pageable
    ){

        OrderRequestFilter filter =
                new OrderRequestFilter();


        filter.setKeyword(keyword);

        filter.setStatus(status);


        return ResponseEntity.ok(
                orderService.getAllOrders(
                        filter,
                        pageable
                )
        );

    }
    @GetMapping("/my-orders")
    public ResponseEntity<List<OrderResponseDTO>> getMyOrders(){

        return ResponseEntity.ok(
                orderService.getMyOrders()
        );

    }
}
