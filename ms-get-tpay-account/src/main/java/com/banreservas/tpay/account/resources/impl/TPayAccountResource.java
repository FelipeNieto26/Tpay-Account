package com.banreservas.tpay.account.resources.impl;

import com.banreservas.tpay.account.dtos.TPayAccountRequest;
import com.banreservas.tpay.account.exceptions.dto.DataBaseException;
import com.banreservas.tpay.account.resources.ITPayAccountResource;
import com.banreservas.tpay.account.services.IServiceTPayAccount;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.quarkus.runtime.annotations.RegisterForReflection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.common.jaxrs.RestResponseImpl;
import org.jboss.resteasy.reactive.common.jaxrs.StatusTypeImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase que expone un servicio rest para desenmascarar un numero de cuenta
 *
 * @author Jonathan Stiven Peña <jopena@redhat.com>
 * @version 1.0
 * @since 04/09/2024
 */

@Path("/tpay/getDetailAccountByProduct")
//@Authenticated
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterForReflection(targets = {RestResponseImpl.class, StatusTypeImpl.class, DataBaseException.class, ConstraintViolationException.class, InvalidFormatException.class})
public class TPayAccountResource implements ITPayAccountResource {


    static final  Logger log = LoggerFactory.getLogger(TPayAccountResource.class);

    IServiceTPayAccount serviceTPayAccount;

    @Inject
    public TPayAccountResource(IServiceTPayAccount serviceTPayAccount) {
        this.serviceTPayAccount = serviceTPayAccount;
    }

    /**
     * Descripción
     *
     * @param tPayAccountRequest recibe los datos de request del servicio:
     * @return
     * @throws
     */

    @POST()
    public Uni<Response> getDetailAccountByProduct(@Valid TPayAccountRequest tPayAccountRequest,
                                                   @HeaderParam("date") @NotNull String date,
                                                   @HeaderParam("channel") @NotNull String channel,
                                                   @HeaderParam("terminal") @NotNull String terminal,
                                                   @HeaderParam("user") @NotNull String user,
                                                   @HeaderParam("operationName") @NotNull String operationName) {

        log.info("Se inicia la Transaccion de desemascarar cuenta {}" , tPayAccountRequest);

        return serviceTPayAccount.unmaskProduct(tPayAccountRequest);


    }


}
