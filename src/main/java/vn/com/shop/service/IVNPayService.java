package vn.com.shop.service;

import java.math.BigDecimal;

public interface IVNPayService {
    /**
     * Tạo link thanh toán VNPay
     *
     * @param orderId mã đơn hàng
     * @param amount số tiền thanh toán
     * @return URL redirect sang VNPay
     */
    String createPaymentUrl(
            Long orderId,
            BigDecimal amount
    );



    /**
     * Kiểm tra chữ ký callback từ VNPay
     *
     * @param params dữ liệu VNPay trả về
     * @return true nếu hợp lệ
     */
    boolean verifyPayment(
            java.util.Map<String,String> params
    );
}
