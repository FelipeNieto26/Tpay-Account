package com.banreservas.tpay.account.services.impl;

import com.banreservas.tpay.account.dtos.ResponseDto;
import com.banreservas.tpay.account.dtos.ResponseHeaderDto;
import com.banreservas.tpay.account.dtos.TPayAccountRequest;
import com.banreservas.tpay.account.dtos.TPayAccountResponse;
import com.banreservas.tpay.account.repositories.ITPayRepository;
import com.banreservas.tpay.account.services.IServiceTPayAccount;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class ServiceTPayAccount implements IServiceTPayAccount {


    ITPayRepository itPayRepository;

    @Inject
    public ServiceTPayAccount(ITPayRepository itPayRepository) {
        this.itPayRepository = itPayRepository;
    }

    @Override
    public Uni<Response> unmaskProduct(TPayAccountRequest tPayAccountRequest) {

        return itPayRepository.getUnmaskProduct(tPayAccountRequest)
                .onItem().transform(tPayAccountResponse -> {
                    ResponseDto<TPayAccountResponse> responseRest;

                    if (tPayAccountResponse != null && "T10".equals(tPayAccountResponse.result())) {
                        responseRest = new ResponseDto<>(new ResponseHeaderDto(Response.Status.NOT_FOUND.getStatusCode(), "Cuenta No encontrada"), null);
                        return Response.status(Response.Status.NOT_FOUND).entity(responseRest).build();
                    }
                    responseRest = new ResponseDto<>(new ResponseHeaderDto(Response.Status.OK.getStatusCode(), "Operacion Exitosa"), tPayAccountResponse);
                    return Response.status(Response.Status.OK)
                            .entity(responseRest)
                            .build();
                }).onFailure().recoverWithItem(throwable -> {
                    ResponseDto<String> responseRest = new ResponseDto<>(new ResponseHeaderDto(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "Error interno"), null);
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseRest).build();
                });


    }
}
