package com.banreservas.tpay.account.repositories;

import com.banreservas.tpay.account.dtos.TPayAccountRequest;
import com.banreservas.tpay.account.dtos.TPayAccountResponse;
import io.smallrye.mutiny.Uni;

public interface ITPayRepository {
    Uni<TPayAccountResponse> getUnmaskProduct(TPayAccountRequest tPayAccountRequest);
}
