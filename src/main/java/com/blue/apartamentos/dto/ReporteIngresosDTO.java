package com.blue.apartamentos.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ReporteIngresosDTO {
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private BigDecimal totalIngresos;
    private int cantidadPagosRegistrados;

    public ReporteIngresosDTO() {}

    public ReporteIngresosDTO(LocalDate fechaInicio, LocalDate fechaFin, BigDecimal totalIngresos, int cantidadPagosRegistrados) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.totalIngresos = totalIngresos;
        this.cantidadPagosRegistrados = cantidadPagosRegistrados;
    }

    // Getters y Setters
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

    public BigDecimal getTotalIngresos() {
        return totalIngresos;
    }

    public void setTotalIngresos(BigDecimal totalIngresos) {
        this.totalIngresos = totalIngresos;
    }

    public int getCantidadPagosRegistrados() {
        return cantidadPagosRegistrados;
    }

    public void setCantidadPagosRegistrados(int cantidadPagosRegistrados) {
        this.cantidadPagosRegistrados = cantidadPagosRegistrados;
    }

    
}
