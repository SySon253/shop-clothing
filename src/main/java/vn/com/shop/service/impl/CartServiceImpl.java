package vn.com.shop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
        return userRepository.findById(1L).orElseThrow(() -> new RuntimeException("User Not Found"));
    }
    @Override
    public CartResponseDTO getMyCart() {
        UserEntity user = getCurrentUser();
        CartEntity cart = cartRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("Cart Not Found"));
        return cartMapper.entityToDto(cart);
    }

    @Override
    public CartResponseDTO addToCart(AddCartItemRequestDTO addCartItemRequestDTO) {
        UserEntity user = getCurrentUser();
        CartEntity cart = cartRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("Cart Not Found"));
        ProductVariantEntity variant = productVariantRepository.findById(addCartItemRequestDTO.getVariantId()).orElseThrow(() -> new RuntimeException("Variant Not Found"));
        if (variant.getStock() < addCartItemRequestDTO.getQuantity()){
            throw new RuntimeException("Stock Not Enough");
        }
        Optional<CartItemEntity> existingItem = cartItemRepository.findByCartIdAndVariantId(cart.getId(),variant.getId());
        if (existingItem.isPresent()){
            CartItemEntity cartItemEntity = existingItem.get();
            int newQuantity = cartItemEntity.getQuantity() + addCartItemRequestDTO.getQuantity();
            if(newQuantity > variant.getStock()){
                throw new RuntimeException("Not enough stock");
            }
            cartItemEntity.setQuantity(newQuantity);
            cartItemRepository.save(cartItemEntity);
        }else {
            CartItemEntity cartItemEntity = new CartItemEntity();
            cartItemEntity.setCart(cart);
            cartItemEntity.setVariant(variant);
            cartItemEntity.setQuantity(addCartItemRequestDTO.getQuantity());
            cartItemRepository.save(cartItemEntity);
        }
        return cartMapper.entityToDto(cart);
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
