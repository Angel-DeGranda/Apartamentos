package com.blue.apartamentos.models;

public class OcupacionReporte {
    private Long idPropiedad;
    private String tituloPropiedad;
    private String tipoPropiedad;
    private long totalReservas;
    private long diasReservados;

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
