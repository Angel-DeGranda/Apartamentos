package com.blue.apartamentos.exceptions;

// Lógica para cuando una regla de negocio falla (ej: fechas empalmadas, correos duplicados) (Retorna HTTP 400)
public class BadRequestException extends RuntimeException {
    public BadRequestException(String mensaje) {
        super(mensaje);
    }
}
