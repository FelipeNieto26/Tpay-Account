package com.banreservas.tpay.account.exceptions.dto;

public class DataBaseException extends RuntimeException{
    /**
     * Constructor por defecto
     * @param message recibe el mensaje de error
     */
    public DataBaseException(String message) {
        super(message);
    }
}
