package com.banreservas.tpay.account.dtos;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record ResponseHeaderDto(int responseCode
        , String responseMessage) {
}
