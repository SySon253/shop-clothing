package vn.com.shop.controller.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.com.shop.service.IPaymentService;

import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final IPaymentService paymentService;
    @GetMapping("/vnpay-return")
    public String vnpayReturn(@RequestParam Map<String,String> params
    ){
        String result = paymentService.handleVNPayCallback(params);
        if("00".equals(result)){
            return """
                    <h2>
                    Thanh toán thành công
                    </h2>
                    """;
        }
        return """
                <h2>
                Thanh toán thất bại
                </h2>
                """;
    }
}
