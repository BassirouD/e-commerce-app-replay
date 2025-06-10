package com.koula.ecommerce.payment;

import com.koula.ecommerce.notification.NotificationProducer;
import com.koula.ecommerce.notification.PaymentNotificationRequest;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository repository;
    private final PaymentMapper mapper;
    private final NotificationProducer notificationProducer;

    public PaymentService(PaymentRepository repository, PaymentMapper mapper, NotificationProducer notificationProducer) {
        this.repository = repository;
        this.mapper = mapper;
        this.notificationProducer = notificationProducer;
    }

    public Integer createPayment(PaymentRequest request) {
        var payment = repository.save(mapper.toPayment(request));
        notificationProducer.sendNotification(
                new PaymentNotificationRequest(
                        request.orderReference(),
                        request.amount(),
                        request.paymentMethod(),
                        request.customer().firstName(),
                        request.customer().lastName(),
                        request.customer().email()
                )
        );
        return payment.getId();
    }
}
