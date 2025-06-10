package com.koula.ecommerce.order;


import com.koula.ecommerce.orderline.OrderLine;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "customer_order")
public class Order {
    @Id
    @GeneratedValue
    private Integer id;
    private String reference;
    private BigDecimal totalAmount;
    @Enumerated(EnumType.STRING)
    private PaymentMethode paymentMethode;
    private String customerId;
    @OneToMany(mappedBy = "order")
    private List<OrderLine> orderLines;
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdDate;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public PaymentMethode getPaymentMethode() {
        return paymentMethode;
    }

    public void setPaymentMethode(PaymentMethode paymentMethode) {
        this.paymentMethode = paymentMethode;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public static class Builder {
        private final Order instance;

        public Builder() {
            instance = new Order();
        }

        public Builder id(Integer id) {
            instance.id = id;
            return this;
        }

        public Builder reference(String reference) {
            instance.reference = reference;
            return this;
        }

        public Builder totalAmount(BigDecimal totalAmount) {
            instance.totalAmount = totalAmount;
            return this;
        }

        public Builder paymentMethode(PaymentMethode paymentMethode) {
            instance.paymentMethode = paymentMethode;
            return this;
        }

        public Builder customerId(String customerId) {
            instance.customerId = customerId;
            return this;
        }

        public Builder orderLines(List<OrderLine> orderLines) {
            instance.orderLines = orderLines;
            return this;
        }

        public Builder createdDate(LocalDateTime createdDate) {
            instance.createdDate = createdDate;
            return this;
        }

        public Builder lastModifiedDate(LocalDateTime lastModifiedDate) {
            instance.lastModifiedDate = lastModifiedDate;
            return this;
        }

        public Order build() {
            return instance;
        }
    }

}
