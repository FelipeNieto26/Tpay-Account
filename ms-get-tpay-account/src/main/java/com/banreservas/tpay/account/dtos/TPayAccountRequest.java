package com.banreservas.tpay.account.dtos;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@RegisterForReflection
public record TPayAccountRequest(
        @NotNull(message = "Product number is required")
        @NotBlank(message = "Product number is required")
        String producNumber,

        @NotNull(message = "Product type is required")
        @NotBlank(message = "Product type is required")
        String producType
) {

}
