package com.koula.ecommerce.order;

import org.springframework.stereotype.Service;

@Service
public class OrderMapper {
    public Order toOrder(OrderRequest request) {
        return new Order.Builder()
                .id(request.id())
                .reference(request.reference())
                .totalAmount(request.totalAmount())
                .paymentMethode(request.paymentMethode())
                .customerId(request.customerId())
                .build();
    }

    public OrderResponse fromOrder(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getReference(),
                order.getTotalAmount(),
                order.getPaymentMethode(),
                order.getCustomerId()
        );
    }
}
