package com.koula.ecommerce.payment;

import com.koula.ecommerce.customer.CustomerResponse;
import com.koula.ecommerce.order.PaymentMethode;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethode paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
