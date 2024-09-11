package com.banreservas.tpay.account.services;

import com.banreservas.tpay.account.dtos.TPayAccountRequest;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.core.Response;

@FunctionalInterface
public interface IServiceTPayAccount {

    Uni<Response> unmaskProduct(TPayAccountRequest tPayAccountRequest);
}
