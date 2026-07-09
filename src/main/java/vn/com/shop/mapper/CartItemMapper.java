package vn.com.shop.mapper;

import org.springframework.stereotype.Component;
import vn.com.shop.dto.cart.CartItemResponseDTO;
import vn.com.shop.entity.CartItemEntity;
import vn.com.shop.entity.ProductEntity;
import vn.com.shop.entity.ProductVariantEntity;

import java.math.BigDecimal;

@Component
public class CartItemMapper {
    public CartItemResponseDTO cartItemToDto(CartItemEntity cartItemEntity){
        if (cartItemEntity == null) return null;
        ProductVariantEntity productVariantEntity = cartItemEntity.getVariant();
        ProductEntity productEntity = productVariantEntity.getProduct();
        CartItemResponseDTO cartItemResponseDTO  = new CartItemResponseDTO();
        cartItemResponseDTO.setId(cartItemEntity.getId());
        cartItemResponseDTO.setVariantId(productVariantEntity.getId());
        cartItemResponseDTO.setProductName(productEntity.getName());
        cartItemResponseDTO.setSku(productVariantEntity.getSku());
        cartItemResponseDTO.setSize(productVariantEntity.getSize());
        cartItemResponseDTO.setColor(productVariantEntity.getColor());

        BigDecimal price =
                productVariantEntity.getDiscountPrice() != null
                        ? productVariantEntity.getDiscountPrice()
                        : productVariantEntity.getPrice();

        cartItemResponseDTO.setPrice(price);
        cartItemResponseDTO.setQuantity(cartItemEntity.getQuantity());
        cartItemResponseDTO.setSubtotal(cartItemEntity.getQuantity() * price.doubleValue());

        return cartItemResponseDTO;
    }
}
