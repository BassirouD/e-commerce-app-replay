package com.koula.ecommerce.kafka;

import com.koula.ecommerce.customer.CustomerResponse;
import com.koula.ecommerce.order.PaymentMethode;
import com.koula.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethode paymentMethode,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
