package com.blue.apartamentos.dto;

public class ReporteOcupacionDTO {
    
    private Long idPropiedad;
    private String tituloPropiedad;
    private String tipoPropiedad;
    private long totalReservas;
    private long diasReservados;

    public ReporteOcupacionDTO() {}

    public ReporteOcupacionDTO(Long idPropiedad, String tituloPropiedad, String tipoPropiedad, long totalReservas, long diasReservados) {
        this.idPropiedad = idPropiedad;
        this.tituloPropiedad = tituloPropiedad;
        this.tipoPropiedad = tipoPropiedad;
        this.totalReservas = totalReservas;
        this.diasReservados = diasReservados;
    }

    // Getters y Setters
    public Long getIdPropiedad() {
        return idPropiedad;
    }

    public void setIdPropiedad(Long idPropiedad) {
        this.idPropiedad = idPropiedad;
    }

    public String getTituloPropiedad() {
        return tituloPropiedad;
    }

    public void setTituloPropiedad(String tituloPropiedad) {
        this.tituloPropiedad = tituloPropiedad;
    }

    public String getTipoPropiedad() {
        return tipoPropiedad;
    }

    public void setTipoPropiedad(String tipoPropiedad) {
        this.tipoPropiedad = tipoPropiedad;
    }

    public long getTotalReservas() {
        return totalReservas;
    }

    public void setTotalReservas(long totalReservas) {
        this.totalReservas = totalReservas;
    }

    public long getDiasReservados() {
        return diasReservados;
    }

    public void setDiasReservados(long diasReservados) {
        this.diasReservados = diasReservados;
    }
}
