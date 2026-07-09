package vn.com.shop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.shop.dto.cart.AddCartItemRequestDTO;
import vn.com.shop.dto.cart.CartResponseDTO;
import vn.com.shop.entity.*;
import vn.com.shop.mapper.CartMapper;
import vn.com.shop.repository.CartItemRepository;
import vn.com.shop.repository.CartRepository;
import vn.com.shop.repository.ProductVariantRepository;
import vn.com.shop.repository.UserRepository;
import vn.com.shop.service.ICartService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements ICartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductVariantRepository productVariantRepository;
    private final UserRepository userRepository;
    private final CartMapper cartMapper;

    private UserEntity getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();


        String username = authentication.getName();


        return userRepository
                .findFirstByUsername(username)
                .orElseThrow(
                        () -> new RuntimeException(
                                "User Not Found"
                        )
                );
    }

@Override
public CartResponseDTO getMyCart() {

    UserEntity user = getCurrentUser();


    System.out.println(
            "CURRENT USER: "
                    + user.getUsername()
                    + " ID: "
                    + user.getId()
    );


    CartEntity cart =
            cartRepository
                    .findByUserId(user.getId())
                    .orElseThrow(
                            () -> new RuntimeException("Cart Not Found")
                    );


    System.out.println(
            "CURRENT CART ID: "
                    + cart.getId()
    );


    return cartMapper.entityToDto(cart);
}
@Override
@Transactional
public CartResponseDTO addToCart(
        AddCartItemRequestDTO request
) {

    UserEntity user = getCurrentUser();


    CartEntity cart =
            cartRepository
                    .findByUserId(user.getId())
                    .orElseGet(() -> {

                        CartEntity newCart =
                                new CartEntity();

                        newCart.setUser(user);

                        return cartRepository.save(newCart);

                    });




    ProductVariantEntity variant =
            productVariantRepository
                    .findById(request.getVariantId())
                    .orElseThrow(
                            () -> new RuntimeException(
                                    "Variant Not Found"
                            )
                    );


    if(
            variant.getStock()
                    < request.getQuantity()
    ){
        throw new RuntimeException(
                "Stock Not Enough"
        );
    }



    CartItemEntity item =
            cartItemRepository
                    .findByCartIdAndVariantId(
                            cart.getId(),
                            variant.getId()
                    )
                    .orElse(null);



    if(item != null){

        int quantity =
                item.getQuantity()
                        + request.getQuantity();


        if(quantity > variant.getStock()){
            throw new RuntimeException(
                    "Not enough stock"
            );
        }


        item.setQuantity(quantity);

        cartItemRepository.save(item);

    }
    else{

        CartItemEntity newItem =
                new CartItemEntity();

        newItem.setCart(cart);

        newItem.setVariant(variant);

        newItem.setQuantity(
                request.getQuantity()
        );


        cartItemRepository.save(newItem);

    }


    CartEntity result =
            cartRepository
                    .findById(cart.getId())
                    .orElseThrow();


    return cartMapper.entityToDto(result);

}

    @Override
    public CartResponseDTO updateCartItem(Long cartItemId, Integer quantity) {
        CartItemEntity item = cartItemRepository.findById(cartItemId).orElseThrow(() -> new RuntimeException("Cart Item Not Found"));
        ProductVariantEntity variant = item.getVariant();
        if (quantity > variant.getStock()){
            throw new RuntimeException("Stock Not Enough");
        }
        item.setQuantity(quantity);
        cartItemRepository.save(item);
        return cartMapper.entityToDto(item.getCart());
    }

    @Override
    public void deleteCartItem(Long cartItemId) {
        CartItemEntity item = cartItemRepository.findById(cartItemId).orElseThrow(() -> new RuntimeException("Cart Item Not Found"));
        cartItemRepository.delete(item);
    }

    @Override
    public void clearCart() {
        UserEntity user = getCurrentUser();
        CartEntity cart = cartRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("Cart Not Found"));
        cartItemRepository.deleteAll(cart.getItems());
    }
}
