package vn.com.shop.controller.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.shop.dto.cart.AddCartItemRequestDTO;
import vn.com.shop.dto.cart.CartResponseDTO;
import vn.com.shop.dto.cart.UpdateCartItemRequestDTO;
import vn.com.shop.service.ICartService;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartResourceController {
    private final ICartService iCartService;
    @GetMapping("/my-cart")
    public ResponseEntity<CartResponseDTO> getMyCart() {
        return ResponseEntity.ok(iCartService.getMyCart());
    }

    @PostMapping("/item")
    public ResponseEntity<CartResponseDTO> addItemToCart(@RequestBody AddCartItemRequestDTO addCartItemRequestDTO) {
        return ResponseEntity.ok(iCartService.addToCart(addCartItemRequestDTO));
    }

    @PutMapping("/item/{id}")
    public ResponseEntity<CartResponseDTO> updateItemToCart(@RequestBody UpdateCartItemRequestDTO updateCartItemRequestDTO, @PathVariable Long id) {
        return ResponseEntity.ok(iCartService.updateCartItem(id, updateCartItemRequestDTO.getQuantity()));
    }

    @DeleteMapping("/item/{id}")
    public ResponseEntity<CartResponseDTO> deleteItemFromCart(@PathVariable Long id) {
        iCartService.deleteCartItem(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clear")
    public ResponseEntity<CartResponseDTO> clearCart() {
        iCartService.clearCart();
        return ResponseEntity.noContent().build();
    }
}
