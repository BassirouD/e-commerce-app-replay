package com.koula.ecommerce.payment;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Valid
public record Customer(
        String id,
        @NotNull(message = "FirstName is required")
        String firstName,
        @NotNull(message = "LastName is required")
        String lastName,
        @NotNull(message = "Email is required")
        @Email(message = "The customer email is not valid")
        String email
) {
}
