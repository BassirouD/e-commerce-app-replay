package com.koula.ecommerce.order;

import com.koula.ecommerce.customer.CustomerClient;
import com.koula.ecommerce.exception.BusinessException;
import com.koula.ecommerce.kafka.OrderConfirmation;
import com.koula.ecommerce.kafka.OrderProducer;
import com.koula.ecommerce.orderline.OrderLineRequest;
import com.koula.ecommerce.orderline.OrderLineService;
import com.koula.ecommerce.payment.PaymentClient;
import com.koula.ecommerce.payment.PaymentRequest;
import com.koula.ecommerce.product.ProductClient;
import com.koula.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class OrderService {
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    public OrderService(CustomerClient customerClient, ProductClient productClient, OrderRepository repository, OrderMapper mapper, OrderLineService orderLineService, OrderProducer orderProducer, PaymentClient paymentClient) {
        this.customerClient = customerClient;
        this.productClient = productClient;
        this.repository = repository;
        this.mapper = mapper;
        this.orderLineService = orderLineService;
        this.orderProducer = orderProducer;
        this.paymentClient = paymentClient;
    }

    public Integer createOrder(OrderRequest request) {
        //Check customer --> OpenFeign
        var customer = customerClient.findCustomerById(request.customerId()).orElseThrow(
                () -> new BusinessException("Cannot create order:: No customer found with customer id: " + request.customerId())
        );

        //purchase product --> RestTemplate
        var purchaseProducts = this.productClient.purchaseProduct(request.products());

        //persist order
        var order = repository.save(mapper.toOrder(request));

        //persist order line
        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        // todo start payment process

        var paymentRequest = new PaymentRequest(
                request.totalAmount(),
                request.paymentMethode(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);

        //send order confirmation
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.totalAmount(),
                        request.paymentMethode(),
                        customer,
                        purchaseProducts
                )
        );

        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer orderId) {
        return repository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find order:: No order found with id: " + orderId));
    }
}
