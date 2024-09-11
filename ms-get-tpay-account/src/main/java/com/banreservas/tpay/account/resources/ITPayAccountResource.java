package com.banreservas.tpay.account.resources;

import com.banreservas.tpay.account.dtos.ResponseDto;
import com.banreservas.tpay.account.dtos.TPayAccountRequest;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

public interface ITPayAccountResource {

    @APIResponse(
            responseCode = "200",
            description = "Succesfull Operation, esto devuelve numero de cuenta desmarcado",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ResponseDto.class)))
    @APIResponse(
            responseCode = "204",
            description = "Not Found, no se encontro en numero de cuenta",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ResponseDto.class)))
    @APIResponse(
            responseCode = "503",
            description = "Internal Server Error, error técnico en la base de datos",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ResponseDto.class)))
    Uni<Response> getDetailAccountByProduct(@Valid TPayAccountRequest tPayAccountRequest,
                                            @HeaderParam("date") @NotNull String date,
                                            @HeaderParam("channel") @NotNull String channel,
                                            @HeaderParam("terminal") @NotNull String terminal,
                                            @HeaderParam("user") @NotNull String user,
                                            @HeaderParam("operationName") @NotNull String operationName);
}
