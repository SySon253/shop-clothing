package vn.com.shop.mapper;

import org.springframework.stereotype.Component;
import vn.com.shop.dto.order.OrderItemResponseDTO;
import vn.com.shop.dto.order.OrderResponseDTO;
import vn.com.shop.entity.OrderEntity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Component
public class OrderMapper {


    public OrderResponseDTO entityToDto(
            OrderEntity orderEntity
    ) {

        System.out.println(
                "ORDER ID: " + orderEntity.getId()
        );


        System.out.println(
                "Receiver: " + orderEntity.getReceiverName()
        );


        System.out.println(
                "Phone: " + orderEntity.getPhone()
        );


        System.out.println(
                "Address: " + orderEntity.getAddress()
        );


        if(orderEntity == null){
            return null;
        }



        OrderResponseDTO dto =
                new OrderResponseDTO();



        dto.setId(
                orderEntity.getId()
        );
        dto.setOrderCode(
                "ORD" + orderEntity.getId()
        );


        dto.setReceiverName(
                orderEntity.getReceiverName()
        );


        dto.setPhone(
                orderEntity.getPhone()
        );


        dto.setAddress(
                orderEntity.getAddress()
        );


        dto.setPaymentMethod(
                orderEntity.getPaymentMethod()
        );


        dto.setTotalAmount(
                orderEntity.getTotalAmount()
        );

        dto.setCreatedDate(
                orderEntity.getCreatedDate()
        );


        if(orderEntity.getItems()!=null){


            List<OrderItemResponseDTO> items =
                    orderEntity
                            .getItems()
                            .stream()
                            .map(item -> {


                                OrderItemResponseDTO itemDTO =
                                        new OrderItemResponseDTO();



                                itemDTO.setProductName(
                                        item.getProductName()
                                );


                                itemDTO.setSku(
                                        item.getSku()
                                );


                                itemDTO.setSize(
                                        item.getSize()
                                );


                                itemDTO.setColor(
                                        item.getColor()
                                );


                                itemDTO.setPrice(
                                        item.getPrice()
                                );


                                itemDTO.setQuantity(
                                        item.getQuantity()
                                );



                                return itemDTO;


                            })
                            .collect(
                                    Collectors.toList()
                            );



            dto.setItems(items);

        }



        return dto;

    }

}