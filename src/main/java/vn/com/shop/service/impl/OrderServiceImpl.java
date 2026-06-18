package vn.com.shop.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.shop.dto.order.OrderCreateResponseDTO;
import vn.com.shop.dto.order.OrderRequestDTO;
import vn.com.shop.dto.order.OrderResponseDTO;
import vn.com.shop.dto.order.PaymentMethod;
import vn.com.shop.dto.request.OrderRequestFilter;
import vn.com.shop.dto.response.ResponsePage;
import vn.com.shop.entity.*;
import vn.com.shop.mapper.OrderMapper;
import vn.com.shop.repository.*;
import vn.com.shop.service.IOrderService;
import vn.com.shop.service.IVNPayService;


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements IOrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final OrderMapper orderMapper;
    private final PaymentRepository paymentRepository;
    private final IVNPayService vnPayService;
    private final ProductVariantRepository productVariantRepository;
    private final UserRepository userRepository;


    @Override
    public OrderCreateResponseDTO createOrder(OrderRequestDTO request) {

        System.out.println("========== CREATE ORDER ==========");
        System.out.println("CartItemIds = " + request.getCartItemIds());

        UserEntity user = getCurrentUser();

        CartEntity cart = cartRepository
                .findByUserId(user.getId())
                .orElseThrow(() ->
                        new RuntimeException("Không tìm thấy giỏ hàng")
                );


        if (request.getCartItemIds() == null ||
                request.getCartItemIds().isEmpty()) {

            throw new RuntimeException(
                    "Chưa chọn sản phẩm thanh toán"
            );
        }


        Set<CartItemEntity> selectedItems =
                cart.getItems()
                        .stream()
                        .filter(item ->
                                request.getCartItemIds()
                                        .contains(item.getId())
                        )
                        .collect(Collectors.toSet());


        if (selectedItems.isEmpty()) {
            throw new RuntimeException(
                    "Không tìm thấy sản phẩm checkout"
            );
        }


        OrderEntity order = new OrderEntity();

        order.setUser(user);
        order.setReceiverName(request.getReceiverName());
        order.setPhone(request.getPhone());
        order.setAddress(request.getAddress());
        order.setPaymentMethod(request.getPaymentMethod());
        order.setStatus(OrderStatus.WAITING_PAYMENT);


        Set<OrderItemEntity> orderItems = new HashSet<>();

        BigDecimal totalAmount = BigDecimal.ZERO;


        for (CartItemEntity cartItem : selectedItems) {

            System.out.println("======================");
            System.out.println("CartItem ID: " + cartItem.getId());
            System.out.println("Product: " + cartItem.getVariant().getProduct().getName());
            System.out.println("Price: " + cartItem.getVariant().getPrice());
            System.out.println("Quantity: " + cartItem.getQuantity());
            System.out.println("======================");

            ProductVariantEntity variant = cartItem.getVariant();
            if (variant.getStock() < cartItem.getQuantity()) {

                throw new RuntimeException(
                        "Sản phẩm " + variant.getProduct().getName() + " không đủ tồn kho"
                );
            }
            OrderItemEntity orderItem = new OrderItemEntity();
            orderItem.setOrder(order);
            orderItem.setProductName(variant.getProduct().getName());
            orderItem.setSku(variant.getSku());
            orderItem.setSize(variant.getSize());
            orderItem.setColor(variant.getColor());
            BigDecimal checkoutPrice =
                    variant.getDiscountPrice() != null
                            ? variant.getDiscountPrice()
                            : variant.getPrice();

            orderItem.setPrice(checkoutPrice);
            orderItem.setQuantity(cartItem.getQuantity());

            BigDecimal itemTotal =
                    checkoutPrice.multiply(
                            BigDecimal.valueOf(cartItem.getQuantity())
                    );
            totalAmount = totalAmount.add(itemTotal);
            System.out.println(
                    "TOTAL AFTER ADD = "
                            + totalAmount
            );

            orderItems.add(orderItem);
            variant.setStock(
                    variant.getStock()
                            -
                            cartItem.getQuantity()
            );
            productVariantRepository.save(variant);
        }
        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);
        System.out.println(
                "TOTAL ORDER = " + totalAmount
        );
        OrderEntity savedOrder = orderRepository.save(order);
        PaymentEntity payment = new PaymentEntity();
        payment.setOrder(savedOrder);
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setAmount(totalAmount.doubleValue());
        payment.setPaymentMethod(request.getPaymentMethod().name());
        payment.setPaymentStatus("PENDING");
        paymentRepository.save(payment);

        String paymentUrl = null;

        if(request.getPaymentMethod() == PaymentMethod.BANKING) {
            System.out.println("======================");
            System.out.println("PRODUCT TOTAL = " + totalAmount);
            System.out.println("======================");

            System.out.println("SEND VNPay = " + totalAmount);
            paymentUrl = vnPayService.createPaymentUrl(
                            savedOrder.getId(),
                            totalAmount
                    );

        }
        if(request.getPaymentMethod() == PaymentMethod.COD) {
            cart.getItems().removeAll(selectedItems);
            cartRepository.save(cart);
        }
        return OrderCreateResponseDTO.builder()
                .orderId(savedOrder.getId())
                .paymentUrl(paymentUrl)
                .order(
                        orderMapper.entityToDto(savedOrder)
                )
                .build();
    }
    @Override
    @Transactional(readOnly = true)
    public ResponsePage<OrderResponseDTO> getAllOrders(
            OrderRequestFilter request,
            Pageable pageable
    ) {
        Page<OrderEntity> page = orderRepository.findAll(pageable);
        List<OrderResponseDTO> orders =
                page.getContent()
                        .stream()
                        .map(orderMapper::entityToDto)
                        .toList();

        return ResponsePage.<OrderResponseDTO>builder()
                .content(orders)
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .build();
    }
    private UserEntity getCurrentUser(){
        return userRepository
                .findById(1L)
                .orElseThrow(
                        () ->
                                new RuntimeException(
                                        "Không tìm thấy user test"
                                )
                );

    }
//    private UserEntity getCurrentUser() {
//
//        Authentication authentication =
//                SecurityContextHolder
//                        .getContext()
//                        .getAuthentication();
//
//
//        String username =
//                authentication.getName();
//
//
//        return userRepository
//                .findByUsername(username)
//                .orElseThrow(() ->
//                        new RuntimeException(
//                                "Không tìm thấy user"
//                        )
//                );
//    }



}