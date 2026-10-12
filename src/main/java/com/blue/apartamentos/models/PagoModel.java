package com.blue.apartamentos.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "pagos")
public class PagoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Long idPago;

    @NotNull(message = "La reserva vinculada es obligatoria")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_reserva", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private ReservaModel reserva;

    @NotNull(message = "El monto del pago es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El monto del pago debe ser mayor a 0")
    @Column(name = "monto", nullable = false, precision = 12, scale = 2)
    private BigDecimal monto;

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    @Column(name = "fecha_pago")
    private LocalDate fechaPago;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago", length = 30)
    private MetodoPago metodoPago;

    @NotNull(message = "El estado del pago es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private EstadoPago estado = EstadoPago.PENDIENTE;

    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    @PrePersist
    void onCreate() {
        this.fechaRegistro = LocalDateTime.now();
    }

    public enum MetodoPago {
        EFECTIVO,
        TRANSFERENCIA,
        TARJETA_CREDITO,
        TARJETA_DEBITO
    }

    public enum EstadoPago {
        PENDIENTE,
        PAGADO,
        ATRASADO,
        CANCELADO
    }

    // Constructores
    public PagoModel() {

    }

    public PagoModel(@NotNull(message = "La reserva vinculada es obligatoria") ReservaModel reserva,
            @NotNull(message = "El monto del pago es obligatorio") @DecimalMin(value = "0.0", inclusive = false, message = "El monto del pago debe ser mayor a 0") BigDecimal monto,
            @NotNull(message = "La fecha de vencimiento es obligatoria") LocalDate fechaVencimiento,
            LocalDate fechaPago, MetodoPago metodoPago,
            @NotNull(message = "El estado del pago es obligatorio") EstadoPago estado) {
        this.reserva = reserva;
        this.monto = monto;
        this.fechaVencimiento = fechaVencimiento;
        this.fechaPago = fechaPago;
        this.metodoPago = metodoPago;
        this.estado = estado;
    }



    public PagoModel(Long idPago, @NotNull(message = "La reserva vinculada es obligatoria") ReservaModel reserva,
            @NotNull(message = "El monto del pago es obligatorio") @DecimalMin(value = "0.0", inclusive = false, message = "El monto del pago debe ser mayor a 0") BigDecimal monto,
            @NotNull(message = "La fecha de vencimiento es obligatoria") LocalDate fechaVencimiento,
            LocalDate fechaPago, MetodoPago metodoPago,
            @NotNull(message = "El estado del pago es obligatorio") EstadoPago estado, LocalDateTime fechaRegistro) {
        this.idPago = idPago;
        this.reserva = reserva;
        this.monto = monto;
        this.fechaVencimiento = fechaVencimiento;
        this.fechaPago = fechaPago;
        this.metodoPago = metodoPago;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
    }


    // Getters y Setters
    public Long getIdPago() {
        return idPago;
    }

    public void setIdPago(Long idPago) {
        this.idPago = idPago;
    }

    public ReservaModel getReserva() {
        return reserva;
    }

    public void setReserva(ReservaModel reserva) {
        this.reserva = reserva;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public EstadoPago getEstado() {
        return estado;
    }

    public void setEstado(EstadoPago estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
}