package com.koula.ecommerce.order;

import java.math.BigDecimal;

public record OrderResponse(
        Integer id,
        String reference,
        BigDecimal amount,
        PaymentMethode paymentMethode,
        String customerId
) {
}
