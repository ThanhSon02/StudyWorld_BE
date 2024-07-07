package com.backend.studyworld.Services;

import com.backend.studyworld.Config.VNPayConfig;
import com.backend.studyworld.DTO.response.PaymentRes;
import com.backend.studyworld.Util.VNPayUltil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PaymentService {
    @Autowired
    private  VNPayConfig vnPayConfig;
    public PaymentRes createVnPayPayment(HttpServletRequest request) {
        PaymentRes paymentRes = new PaymentRes();
        long amount = Integer.parseInt(request.getParameter("amount")) * 100L;
        String bankCode = request.getParameter("bankCode");
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_IpAddr", VNPayUltil.getIpAddress(request));
        //build query url
        String queryUrl = VNPayUltil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUltil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUltil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
        paymentRes.setPaymentURL(paymentUrl);
        return paymentRes;
    }
}
