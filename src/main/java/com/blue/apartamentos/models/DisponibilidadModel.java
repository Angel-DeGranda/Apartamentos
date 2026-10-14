package com.blue.apartamentos.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "disponibilidades")
public class DisponibilidadModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_disponibilidad")
    private Long idDisponibilidad;

    @NotNull(message = "La propiedad asociada es obligatoria")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_propiedad", nullable = false)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private PropiedadModel propiedad;

    @NotNull(message = "La fecha de inicio es obligatoria")
    @FutureOrPresent(message = "La fecha de inicio no puede ser una fecha pasada")
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    @FutureOrPresent(message = "La fecha de fin no puede ser una fecha pasada")
    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @NotNull(message = "Debe indicar si el inmueble está disponible")
    @Column(name = "disponible", nullable = false)
    private boolean  disponible;

    @DecimalMin(value = "0.0", inclusive = false, message = "El precio especial debe ser mayor a 0")
    @Column(name = "precio_especial", precision = 12, scale = 2)
    private BigDecimal precioEspecial;

    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    @PrePersist
    void onCreate() {
        this.fechaRegistro = LocalDateTime.now();
    }

    public DisponibilidadModel() {
    }

    public DisponibilidadModel(Long idDisponibilidad,
            @NotNull(message = "La propiedad asociada es obligatoria") PropiedadModel propiedad,
            @NotNull(message = "La fecha de inicio es obligatoria") @FutureOrPresent(message = "La fecha de inicio no puede ser una fecha pasada") LocalDate fechaInicio,
            @NotNull(message = "La fecha de fin es obligatoria") @FutureOrPresent(message = "La fecha de fin no puede ser una fecha pasada") LocalDate fechaFin,
            @NotNull(message = "Debe indicar si el inmueble está disponible") Boolean disponible,
            @DecimalMin(value = "0.0", inclusive = false, message = "El precio especial debe ser mayor a 0") BigDecimal precioEspecial,
            LocalDateTime fechaRegistro) {
        this.idDisponibilidad = idDisponibilidad;
        this.propiedad = propiedad;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.disponible = disponible;
        this.precioEspecial = precioEspecial;
        this.fechaRegistro = fechaRegistro;
    }

    public DisponibilidadModel(@NotNull(message = "La propiedad asociada es obligatoria") PropiedadModel propiedad,
            @NotNull(message = "La fecha de inicio es obligatoria") @FutureOrPresent(message = "La fecha de inicio no puede ser una fecha pasada") LocalDate fechaInicio,
            @NotNull(message = "La fecha de fin es obligatoria") @FutureOrPresent(message = "La fecha de fin no puede ser una fecha pasada") LocalDate fechaFin,
            @NotNull(message = "Debe indicar si el inmueble está disponible") Boolean disponible,
            @DecimalMin(value = "0.0", inclusive = false, message = "El precio especial debe ser mayor a 0") BigDecimal precioEspecial) {
        this.propiedad = propiedad;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.disponible = disponible;
        this.precioEspecial = precioEspecial;
    }

    public Long getIdDisponibilidad() {
        return idDisponibilidad;
    }

    public void setIdDisponibilidad(Long idDisponibilidad) {
        this.idDisponibilidad = idDisponibilidad;
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

    public BigDecimal getPrecioEspecial() {
        return precioEspecial;
    }

    public void setPrecioEspecial(BigDecimal precioEspecial) {
        this.precioEspecial = precioEspecial;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

}
