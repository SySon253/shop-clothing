package vn.com.shop.service;

import java.util.Map;

public interface IPaymentService {
    String handleVNPayCallback(Map<String,String> params
    );
}
