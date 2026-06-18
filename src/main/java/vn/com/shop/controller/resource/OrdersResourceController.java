package vn.com.shop.controller.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.shop.dto.order.OrderCreateResponseDTO;
import vn.com.shop.dto.order.OrderRequestDTO;
import vn.com.shop.dto.order.OrderResponseDTO;
import vn.com.shop.dto.response.ResponsePage;
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
        return ResponseEntity.ok(orderService.createOrder(request));
    }
    @GetMapping("/admin/orders")
    public ResponseEntity<ResponsePage<OrderResponseDTO>> getAllOrders(
            @RequestParam(required = false) String keyword,
            Pageable pageable
    ){
        return ResponseEntity.ok(orderService.getAllOrders(null, pageable));
    }
}
