package vn.com.shop.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vn.com.shop.dto.cart.CartItemResponseDTO;
import vn.com.shop.dto.cart.CartResponseDTO;
import vn.com.shop.entity.CartEntity;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CartMapper {
    private final CartItemMapper cartItemMapper;
    public CartResponseDTO entityToDto(CartEntity cartEntity){
        if (cartEntity == null) return null;

        CartResponseDTO cartResponseDTO = new CartResponseDTO();
        cartResponseDTO.setId(cartEntity.getId());
        cartResponseDTO.setUserId(cartEntity.getUser().getId());
        List<CartItemResponseDTO> items = cartEntity.getItems().stream().map(cartItemMapper::cartItemToDto).toList();
        cartResponseDTO.setItems(items);
        cartResponseDTO.setTotalItems(items.stream().mapToInt(CartItemResponseDTO::getQuantity).sum());
        cartResponseDTO.setTotalPrice(items.stream().mapToDouble(CartItemResponseDTO::getSubtotal).sum());
        return cartResponseDTO;
    }

}
