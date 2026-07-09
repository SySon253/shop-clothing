package vn.com.shop.dto.request;

import lombok.Data;
import vn.com.shop.entity.OrderStatus;

import java.time.LocalDate;

@Data
public class OrderRequestFilter {
    private String keyword;
    private LocalDate fromDate;
    private LocalDate toDate;
    private OrderStatus status;
}
