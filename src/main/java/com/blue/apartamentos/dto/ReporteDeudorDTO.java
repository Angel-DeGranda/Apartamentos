package com.blue.apartamentos.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ReporteDeudorDTO {
    private Long idPago;
    private Long idReserva;
    private String nombreCliente;
    private String emailCliente;
    private String telefonoCliente;
    private BigDecimal montoPendiente;
    private LocalDate fechaVencimiento;

    public ReporteDeudorDTO() {}

    public ReporteDeudorDTO(Long idPago, Long idReserva, String nombreCliente, String emailCliente, String telefonoCliente, BigDecimal montoPendiente, LocalDate fechaVencimiento) {
        this.idPago = idPago;
        this.idReserva = idReserva;
        this.nombreCliente = nombreCliente;
        this.emailCliente = emailCliente;
        this.telefonoCliente = telefonoCliente;
        this.montoPendiente = montoPendiente;
        this.fechaVencimiento = fechaVencimiento;
    }

    // Getters y Setters
    public Long getIdPago() {
        return idPago;
    }

    public void setIdPago(Long idPago) {
        this.idPago = idPago;
    }

    public Long getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Long idReserva) {
        this.idReserva = idReserva;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public BigDecimal getMontoPendiente() {
        return montoPendiente;
    }

    public void setMontoPendiente(BigDecimal montoPendiente) {
        this.montoPendiente = montoPendiente;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    
}
