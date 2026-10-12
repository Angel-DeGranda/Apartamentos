package com.blue.apartamentos.exceptions;

// Lógica para cuando un ID buscado no existe en la BD (Retorna HTTP 404)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String mensaje) {
        super(mensaje);
    }
}
