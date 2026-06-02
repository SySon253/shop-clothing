package vn.com.shop.dto.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderCreateDTO {
    private String receiverName;
    private String phone;
    private String address;
    private String note;
}
