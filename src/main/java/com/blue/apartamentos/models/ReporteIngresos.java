package com.blue.apartamentos.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ReporteIngresos {
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private BigDecimal totalIngresos;
    private long cantidadPagosRegistrados;

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

    public long getCantidadPagosRegistrados() {
        return cantidadPagosRegistrados;
    }

    public void setCantidadPagosRegistrados(long cantidadPagosRegistrados) {
        this.cantidadPagosRegistrados = cantidadPagosRegistrados;
    }

    
}
