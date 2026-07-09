package vn.com.shop.dto.order;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDTO {
    private String receiverName;
    private String phone;
    private String address;
    private String note;
    private PaymentMethod paymentMethod;
    private List<Long> cartItemIds;
}
