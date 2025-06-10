package com.koula.ecommerce.notification;

import com.koula.ecommerce.kafka.order.OrderConfirmation;
import com.koula.ecommerce.kafka.payment.PaymentConfirmation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Document
public class Notification {
    @Id
    private String id;
    private NotificationType type;
    private LocalDateTime notificationDate;
    private OrderConfirmation orderConfirmation;
    private PaymentConfirmation paymentConfirmation;

    private Notification(Builder builder) {
        this.id = builder.id;
        this.type = builder.type;
        this.notificationDate = builder.notificationDate;
        this.orderConfirmation = builder.orderConfirmation;
        this.paymentConfirmation = builder.paymentConfirmation;
    }

    public Notification() {}

    public static class Builder {
        private String id;
        private NotificationType type;
        private LocalDateTime notificationDate;
        private OrderConfirmation orderConfirmation;
        private PaymentConfirmation paymentConfirmation;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder type(NotificationType type) {
            this.type = type;
            return this;
        }

        public Builder notificationDate(LocalDateTime notificationDate) {
            this.notificationDate = notificationDate;
            return this;
        }

        public Builder orderConfirmation(OrderConfirmation orderConfirmation) {
            this.orderConfirmation = orderConfirmation;
            return this;
        }

        public Builder paymentConfirmation(PaymentConfirmation paymentConfirmation) {
            this.paymentConfirmation = paymentConfirmation;
            return this;
        }

        public Notification build() {
            return new Notification(this);
        }
    }
}
