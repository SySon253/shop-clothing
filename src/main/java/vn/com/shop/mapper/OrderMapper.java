package vn.com.shop.mapper;

import org.springframework.stereotype.Component;
import vn.com.shop.dto.order.OrderItemResponseDTO;
import vn.com.shop.dto.order.OrderResponseDTO;
import vn.com.shop.entity.OrderEntity;

import java.util.Set;

@Component
public class OrderMapper {
    public OrderResponseDTO entityToDto(OrderEntity orderEntity) {
        if (orderEntity == null) {
            return null;
        }
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setId(orderEntity.getId());
        orderResponseDTO.setReceiverName(orderEntity.getReceiverName());
        orderResponseDTO.setPhone(orderEntity.getPhone());
        orderResponseDTO.setAddress(orderEntity.getAddress());
        orderResponseDTO.setPaymentMethod(orderEntity.getPaymentMethod());
        orderResponseDTO.setTotalAmount(orderEntity.getTotalAmount());
        if (orderEntity.getItems() != null){
            Set<OrderItemResponseDTO> items = orderEntity.getItems().stream()
                    .map(item -> {
                        OrderItemResponseDTO itemResponseDTO = new OrderItemResponseDTO();
                        itemResponseDTO.setProductName(item.getProductName());
                        itemResponseDTO.setSku(item.getSku());
                        itemResponseDTO.setSize(item.getSize());
                        itemResponseDTO.setColor(item.getColor());
                        itemResponseDTO.setPrice(item.getPrice());
                        itemResponseDTO.setQuantity(item.getQuantity());
                        return itemResponseDTO;
                    })
                    .collect(java.util.stream.Collectors.toSet());
            orderResponseDTO.setItems(items);
        }
        return orderResponseDTO;
    }
}
