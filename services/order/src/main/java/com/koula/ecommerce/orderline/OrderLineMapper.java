package com.koula.ecommerce.orderline;

import com.koula.ecommerce.order.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderLineMapper {
    public OrderLine toOrderLine(OrderLineRequest request) {
        return new OrderLine.Builder()
                .id(request.id())
                .productId(request.productId())
                .quantity(request.quantity())
                .order(new Order.Builder()
                        .id(request.orderId())
                        .build()
                )
                .build();
    }

    public OrderLineResponse toOrderLineResponse(OrderLine orderLine) {
        return new OrderLineResponse(orderLine.getId(), orderLine.getQuantity());
    }
}
