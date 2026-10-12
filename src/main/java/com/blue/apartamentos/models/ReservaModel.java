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
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "reservas")
public class ReservaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva")
    private Long idReserva;

    @NotNull(message = "El arrendatario es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_arrendatario", nullable = false)
    @JsonIgnoreProperties({"password", "hibernateLazyInitializer", "handler"})
    private ClienteModel arrendatario;

    @NotNull(message = "La propiedad es obligatoria")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_propiedad", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private PropiedadModel propiedad;

    @NotNull(message = "La fecha de inicio es obligatoria")
    @FutureOrPresent(message = "La fecha de inicio no puede ser del pasado")
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha final es obligatoria")
    @FutureOrPresent(message = "La fecha de fin no puede ser del pasado")
    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @NotNull(message = "El precio total es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio total debe ser mayor a 0")
    @Column(name = "precio_total", nullable = false, precision = 12, scale = 2)
    private BigDecimal precioTotal;

    @NotNull(message = "El estado de la reserva es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private EstadoReserva estado = EstadoReserva.PENDIENTE;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist
    void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
    }

    public enum EstadoReserva {
        PENDIENTE,
        CONFIRMADA,
        CANCELADA,
        FINALIZADA
    }


    // Constructores
    public ReservaModel() {}


    public ReservaModel(Long idReserva, @NotNull(message = "El arrendatario es obligatorio") ClienteModel arrendatario,
            @NotNull(message = "La propiedad es obligatoria") PropiedadModel propiedad,
            @NotNull(message = "La fecha de inicio es obligatoria") @FutureOrPresent(message = "La fecha de inicio no puede ser del pasado") LocalDate fechaInicio,
            @NotNull(message = "La fecha final es obligatoria") @FutureOrPresent(message = "La fecha de fin no puede ser del pasado") LocalDate fechaFin,
            @NotNull(message = "El precio total es obligatorio") @DecimalMin(value = "0.0", inclusive = false, message = "El precio total debe ser mayor a 0") BigDecimal precioTotal,
            @NotNull(message = "El estado de la reserva es obligatorio") EstadoReserva estado,
            LocalDateTime fechaCreacion) {
        this.idReserva = idReserva;
        this.arrendatario = arrendatario;
        this.propiedad = propiedad;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.precioTotal = precioTotal;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
    }


    public ReservaModel(@NotNull(message = "El arrendatario es obligatorio") ClienteModel arrendatario,
            @NotNull(message = "La propiedad es obligatoria") PropiedadModel propiedad,
            @NotNull(message = "La fecha de inicio es obligatoria") @FutureOrPresent(message = "La fecha de inicio no puede ser del pasado") LocalDate fechaInicio,
            @NotNull(message = "La fecha final es obligatoria") @FutureOrPresent(message = "La fecha de fin no puede ser del pasado") LocalDate fechaFin,
            @NotNull(message = "El precio total es obligatorio") @DecimalMin(value = "0.0", inclusive = false, message = "El precio total debe ser mayor a 0") BigDecimal precioTotal,
            @NotNull(message = "El estado de la reserva es obligatorio") EstadoReserva estado) {
        this.arrendatario = arrendatario;
        this.propiedad = propiedad;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.precioTotal = precioTotal;
        this.estado = estado;
    }

    //Getters y Setters
    public Long getIdReserva() {
        return idReserva;
    }


    public void setIdReserva(Long idReserva) {
        this.idReserva = idReserva;
    }


    public ClienteModel getArrendatario() {
        return arrendatario;
    }


    public void setArrendatario(ClienteModel arrendatario) {
        this.arrendatario = arrendatario;
    }


    public PropiedadModel getPropiedad() {
        return propiedad;
    }


    public void setPropiedad(PropiedadModel propiedad) {
        this.propiedad = propiedad;
    }


    public LocalDate getFechaInicio() {
        return fechaInicio;
    }


    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }


    public LocalDate getFechaFin() {
        return fechaFin;
    }


    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }


    public BigDecimal getPrecioTotal() {
        return precioTotal;
    }


    public void setPrecioTotal(BigDecimal precioTotal) {
        this.precioTotal = precioTotal;
    }


    public EstadoReserva getEstado() {
        return estado;
    }


    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }


    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }


    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    

}