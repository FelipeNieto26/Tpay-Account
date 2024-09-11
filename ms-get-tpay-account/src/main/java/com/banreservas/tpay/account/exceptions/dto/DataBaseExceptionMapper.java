package com.banreservas.tpay.account.exceptions.dto;

import com.banreservas.tpay.account.dtos.ResponseDto;
import com.banreservas.tpay.account.dtos.ResponseHeaderDto;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DataBaseExceptionMapper implements ExceptionMapper<DataBaseException> {

    /**
     * toResponse Metodo que devuelve la respuesta en caso de una excepcion
     *
     * @param e recibe la excepcion
     * @return Response devuelve la respuesta con el mensaje de error y el codigo de error
     */
    @Override
    public Response toResponse(DataBaseException e) {
        return Response
                .status(Response.Status.SERVICE_UNAVAILABLE)
                .entity(new ResponseDto(new ResponseHeaderDto(Response.Status.SERVICE_UNAVAILABLE.getStatusCode(), e.getMessage()), null))
                .build();
    }
}

