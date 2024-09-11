package com.banreservas.tpay.account.dtos;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record ResponseDto<T>(ResponseHeaderDto headers,
                             T body) {


}
