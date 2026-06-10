package vn.com.shop.service;

import vn.com.shop.dto.cart.AddCartItemRequestDTO;
import vn.com.shop.dto.cart.CartResponseDTO;

public interface ICartService {
    CartResponseDTO getMyCart();
    CartResponseDTO addToCart(AddCartItemRequestDTO addCartItemRequestDTO);
    CartResponseDTO updateCartItem(Long cartItemId, Integer quantity);
    void deleteCartItem(Long cartItemId);
    void clearCart();
}
