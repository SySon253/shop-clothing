package vn.com.shop.dto.order;

import lombok.Data;

import java.util.List;

@Data
public class OrderCreateRequestDTO {
    private String receiverName;


    private String phone;


    private String address;



    private PaymentMethod paymentMethod;



    private List<Long> cartItemIds;
}
