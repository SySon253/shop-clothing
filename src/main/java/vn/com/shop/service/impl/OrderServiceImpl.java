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
import vn.com.shop.specification.OrderSpecification;


import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements IOrderService {


    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderMapper orderMapper;

    private final PaymentRepository paymentRepository;

    private final IVNPayService vnPayService;

    private final ProductVariantRepository productVariantRepository;

    private final UserRepository userRepository;


    // =====================================================
    // CREATE ORDER
    // =====================================================

    @Override
    public OrderCreateResponseDTO createOrder(
            OrderRequestDTO request
    ) {
        System.out.println("===== CREATE ORDER CONTROLLER =====");
        System.out.println("=================");
        System.out.println("receiverName = "
                + request.getReceiverName());

        System.out.println("phone = "
                + request.getPhone());

        System.out.println("address = "
                + request.getAddress());

        System.out.println("=================");
        System.out.println(
                "========== CREATE ORDER =========="
        );
        UserEntity user = getCurrentUser();
        CartEntity cart = cartRepository
                        .findByUserId(
                                user.getId()
                        )
                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "Không tìm thấy cart"
                                        )
                        );
        if(request.getCartItemIds()==null || request.getCartItemIds().isEmpty()){
            throw new RuntimeException(
                    "Chưa chọn sản phẩm checkout"
            );

        }
        // lấy các sản phẩm được chọn
        Set<CartItemEntity> selectedItems =
                cart.getItems()
                        .stream()
                        .filter(
                                item ->
                                        request
                                                .getCartItemIds()
                                                .contains(item.getId())
                        )
                        .collect(
                                Collectors.toSet()
                        );
        if(selectedItems.isEmpty()){
            throw new RuntimeException(
                    "Không tìm thấy sản phẩm"
            );
        }
        // =================================================
        // CREATE ORDER
        // =================================================
        OrderEntity order = new OrderEntity();
        order.setUser(user);
        order.setReceiverName(request.getReceiverName());
        order.setPhone(request.getPhone());
        order.setAddress(request.getAddress());
        order.setPaymentMethod(request.getPaymentMethod());
        order.setStatus(OrderStatus.WAITING_PAYMENT);
        List<OrderItemEntity> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        // =================================================
        // COPY CART -> ORDER ITEM
        // =================================================


        for(
                CartItemEntity cartItem:
                selectedItems
        ){



            ProductVariantEntity variant =
                    cartItem.getVariant();

            // check stock

            if(
                    variant.getStock()
                            <
                            cartItem.getQuantity()
            ){

                throw new RuntimeException(
                        "Sản phẩm "
                                +
                                variant.getProduct().getName()
                                +
                                " không đủ tồn kho"
                );

            }

            OrderItemEntity orderItem =
                    new OrderItemEntity();

            orderItem.setProductVariant(
                    variant
            );

            // lưu cartItemId
            orderItem.setCartItemId(
                    cartItem.getId()
            );



            orderItem.setOrder(
                    order
            );



            orderItem.setProductName(
                    cartItem.getVariant()
                            .getProduct()
                            .getName()
            );


            orderItem.setSku(
                    variant.getSku()
            );


            orderItem.setSize(
                    variant.getSize()
            );


            orderItem.setColor(
                    variant.getColor()
            );



            BigDecimal price =
                    variant.getDiscountPrice()!=null
                            ?
                            variant.getDiscountPrice()
                            :
                            variant.getPrice();



            orderItem.setPrice(
                    price
            );



            orderItem.setQuantity(
                    cartItem.getQuantity()
            );



            BigDecimal itemTotal =
                    price.multiply(
                            BigDecimal.valueOf(
                                    cartItem.getQuantity()
                            )
                    );



            totalAmount =
                    totalAmount.add(
                            itemTotal
                    );



            orderItems.add(
                    orderItem
            );

        }
        order.setItems(
                orderItems
        );


        order.setTotalAmount(
                totalAmount
        );

        // Lưu để lấy ID
        OrderEntity savedOrder =
                orderRepository.save(order);

// Sinh mã đơn
        savedOrder.setOrderCode(
                String.format("ORD%06d", savedOrder.getId())
        );

// Lưu lại
        savedOrder =
                orderRepository.save(savedOrder);



        // =================================================
        // CREATE PAYMENT
        // =================================================


        PaymentEntity payment =
                new PaymentEntity();


        payment.setOrder(
                savedOrder
        );


        payment.setTransactionId(
                UUID.randomUUID()
                        .toString()
        );


        payment.setAmount(
                totalAmount.doubleValue()
        );


        payment.setPaymentMethod(
                request
                        .getPaymentMethod()
                        .name()
        );



        payment.setPaymentStatus(
                "PENDING"
        );



        paymentRepository.save(
                payment
        );






        String paymentUrl = null;



        // =================================================
        // BANKING
        // =================================================

        if(
                request.getPaymentMethod()
                        ==
                        PaymentMethod.BANKING
        ){
            paymentUrl =
                    vnPayService.createPaymentUrl(
                            savedOrder.getId(),
                            totalAmount
                    );
        }
        // =================================================
        // COD
        // =================================================
        if(
                request.getPaymentMethod()
                        ==
                        PaymentMethod.COD
        ){
            removeCartItems(
                    cart,
                    selectedItems
            );
            savedOrder.setStatus(
                    OrderStatus.CONFIRMED
            );
            orderRepository.save(
                    savedOrder
            );
        }
        return OrderCreateResponseDTO
                .builder()

                .orderId(
                        savedOrder.getId()
                )


                .paymentUrl(
                        paymentUrl
                )


                .order(
                        orderMapper.entityToDto(
                                savedOrder
                        )
                )
                .build();


    }

    @Override
    public ResponsePage<OrderResponseDTO> getAllOrders(
            OrderRequestFilter filter,
            Pageable pageable
    ) {


        Page<OrderEntity> orderPage =
                orderRepository.findAll(
                        OrderSpecification.filter(filter),
                        pageable
                );


        List<OrderResponseDTO> content =
                orderPage
                        .getContent()
                        .stream()
                        .map(orderMapper::entityToDto)
                        .toList();



        return new ResponsePage<>(
                orderPage.getNumber(),
                orderPage.getSize(),
                orderPage.getTotalElements(),
                orderPage.getTotalPages(),
                content
        );

    }


    // =====================================================
    // VNPay CALLBACK SUCCESS
    // =====================================================


    @Override
    public void completePayment(
            Long orderId
    ){
        OrderEntity order =
                orderRepository
                        .findById(orderId)
                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "Không tìm thấy order"
                                        )
                        );




        order.setStatus(
                OrderStatus.CONFIRMED
        );




        CartEntity cart =
                cartRepository
                        .findByUserId(
                                order
                                        .getUser()
                                        .getId()
                        )
                        .orElseThrow();




        Set<Long> cartItemIds =
                order
                        .getItems()
                        .stream()
                        .map(
                                OrderItemEntity::getCartItemId
                        )
                        .collect(
                                Collectors.toSet()
                        );





        cart.getItems()
                .removeIf(
                        item ->
                                cartItemIds
                                        .contains(
                                                item.getId()
                                        )
                );




        cartRepository.save(
                cart
        );


        orderRepository.save(
                order
        );

    }






    // =====================================================
    // GET USER ORDERS
    // =====================================================


    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getMyOrders(){
        UserEntity user = getCurrentUser();
        List<OrderEntity> orders = orderRepository
                        .findByUserId(user.getId());
        System.out.println(
                "CURRENT USER = "
                        + user.getId()
        );


        System.out.println(
                "ORDER SIZE = "
                        + orders.size()
        );
        return orders.stream()
                .map(orderMapper::entityToDto)
                .toList();
    }


    // =====================================================
    // REMOVE CART ITEMS
    // =====================================================


    private void removeCartItems(
            CartEntity cart,
            Set<CartItemEntity> items
    ){


        cart.getItems()
                .removeAll(
                        items
                );


        cartRepository.save(
                cart
        );

    }






    // =====================================================
    // CURRENT USER
    // =====================================================


    private UserEntity getCurrentUser(){


        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();



        if(authentication == null ||
                !authentication.isAuthenticated()){

            throw new RuntimeException(
                    "User chưa đăng nhập"
            );

        }



        String username =
                authentication.getName();



        return userRepository
                .findFirstByUsername(username)
                .orElseThrow(
                        () ->
                                new RuntimeException(
                                        "Không tìm thấy user"
                                )
                );

    }


}