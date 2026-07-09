package vn.com.shop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.com.shop.config.VNPayConfig;
import vn.com.shop.service.IVNPayService;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class VNPayServiceImpl implements IVNPayService {
    @Override
    public String createPaymentUrl(Long orderId, BigDecimal amount) {
        if (orderId == null || amount == null) {
            throw new IllegalArgumentException("orderId and amount are required");
        }

        // Normalize to whole VND then VNPay expects amount * 100
        BigDecimal normalized = amount.setScale(0, RoundingMode.HALF_UP);
        BigDecimal amountSent = normalized.multiply(BigDecimal.valueOf(100L));
        String vnpAmount = amountSent.toBigInteger().toString();

        Map<String, String> params = new TreeMap<>();
        params.put("vnp_Version", "2.1.0");
        params.put("vnp_Command", "pay");
        params.put("vnp_TmnCode", VNPayConfig.TMN_CODE);
        params.put("vnp_Amount", vnpAmount);
        params.put("vnp_CurrCode", "VND");
        params.put("vnp_TxnRef", String.valueOf(orderId));
        params.put("vnp_OrderInfo", "Thanh toan don hang " + orderId);
        params.put("vnp_OrderType", "other");
        params.put("vnp_Locale", "vn");
        params.put("vnp_ReturnUrl", VNPayConfig.RETURN_URL);
        params.put("vnp_IpAddr", "127.0.0.1");
        params.put("vnp_CreateDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

        // Build canonical data and encoded query
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> e : params.entrySet()) {
            String k = e.getKey();
            String v = e.getValue() == null ? "" : e.getValue();
            String encodedValue = URLEncoder.encode(v, StandardCharsets.UTF_8);
            if (!first) {
                hashData.append("&");
                query.append("&");
            }
            first = false;
            hashData.append(k).append("=").append(encodedValue);
            query.append(URLEncoder.encode(k, StandardCharsets.UTF_8)).append("=").append(encodedValue);
        }

        String secureHash = hmacSHA512(VNPayConfig.HASH_SECRET, hashData.toString());
        String url = VNPayConfig.VNP_URL + "?" + query.toString() + "&vnp_SecureHash=" + secureHash;
        return url;
    }

    @Override
    public boolean verifyPayment(Map<String, String> params) {
        if (params == null) return false;
        Map<String, String> copy = new TreeMap<>(params);
        String secureHash = copy.remove("vnp_SecureHash");
        copy.remove("vnp_SecureHashType");

        StringBuilder hashData = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> e : copy.entrySet()) {
            String k = e.getKey();
            String v = e.getValue() == null ? "" : e.getValue();
            String encodedValue = URLEncoder.encode(v, StandardCharsets.UTF_8);
            if (!first) hashData.append("&");
            first = false;
            hashData.append(k).append("=").append(encodedValue);
        }

        String checkHash = hmacSHA512(VNPayConfig.HASH_SECRET, hashData.toString());
        return checkHash.equalsIgnoreCase(secureHash);
    }

    private String hmacSHA512(String key, String data) {
        try {
            Mac hmac = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            hmac.init(secretKey);
            byte[] bytes = hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder(bytes.length * 2);
            for (byte b : bytes) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Cannot create HMAC SHA512", e);
        }
    }
}
