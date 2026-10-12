package com.blue.apartamentos.models;

import java.math.BigDecimal;
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
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "propiedades")
public class PropiedadModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_propiedad")
    private Long idPropiedad;

    @NotNull(message = "El propietario es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_propietario", nullable = false)
    @JsonIgnoreProperties({"password", "hibernateLazyInitializer", "handler"})
    private ClienteModel propietario;

    @NotBlank(message = "El tipo de propiedad es obligatorio")
    @Size(max = 40)
    @Column(name = "tipo_propiedad", nullable = false, length = 40)
    private String tipo; // Ej: "PISO", "HABITACION"

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String titulo;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 500)
    @Column(nullable = false, length = 500)
    private String descripcion;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 150)
    @Column(nullable = false, length = 150)
    private String direccion;

    @NotBlank(message = "La ciudad es obligatoria")
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String ciudad;

    @NotBlank(message = "El estado o entidad federativa es obligatorio")
    @Size(max = 50)
    @Column(name = "estado", nullable = false, length = 50)
    private String estadoFederativo;

    @NotBlank(message = "El código postal es obligatorio")
    @Size(max = 10)
    @Column(name = "codigo_postal", nullable = false, length = 10)
    private String codigoPostal;

    @NotBlank(message = "El país es obligatorio")
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String pais;

    @Column(nullable = false)
    private double latitud;

    @Column(nullable = false)
    private double longitud;

    @NotNull(message = "El precio por noche es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    @Column(name = "precio_noche", nullable = false, precision = 12, scale = 2)
    private BigDecimal precioNoche;

    @Min(value = 1, message = "La capacidad mínima es de 1 persona")
    @Column(nullable = false)
    private int capacidad;

    @Min(value = 0, message = "El número de habitaciones no puede ser negativo")
    @Column(name = "num_habitaciones", nullable = false)
    private int numHabitaciones;

    @Min(value = 0, message = "El número de baños no puede ser negativo")
    @Column(name = "num_banos", nullable = false)
    private int numBanos;

    @Min(value = 0, message = "Los metros cuadrados no pueden ser negativos")
    @Column(name = "metro_cuadrados", nullable = false)
    private int metrosCuadrados;

    @Size(max = 500)
    @Column(length = 500)
    private String comodidades;

    @Size(max = 500)
    @Column(length = 500)
    private String reglas;

    @NotNull(message = "El estado de la propiedad es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_hab", nullable = false, length = 20)
    private EstadoPropiedad estado = EstadoPropiedad.DISPONIBLE;

    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    @Column(name = "fecha_actualizacion", nullable = false)
    private LocalDateTime fechaActualizacion;

    // Callbacks
    @PrePersist
    void onCreate() {
        this.fechaRegistro = LocalDateTime.now();
        this.fechaActualizacion = this.fechaRegistro;
    }

    @PreUpdate
    void onUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }

    // Enum
    public enum EstadoPropiedad {
        DISPONIBLE,
        NO_DISPONIBLE,
        MANTENIMIENTO
    }

    public PropiedadModel() {
    }

    public PropiedadModel(Long idPropiedad,
            @NotNull(message = "El propietario es obligatorio") ClienteModel propietario,
            @NotBlank(message = "El tipo de propiedad es obligatorio") @Size(max = 40) String tipo,
            @NotBlank(message = "El título es obligatorio") @Size(max = 100) String titulo,
            @NotBlank(message = "La descripción es obligatoria") @Size(max = 500) String descripcion,
            @NotBlank(message = "La dirección es obligatoria") @Size(max = 150) String direccion,
            @NotBlank(message = "La ciudad es obligatoria") @Size(max = 50) String ciudad,
            @NotBlank(message = "El estado o entidad federativa es obligatorio") @Size(max = 50) String estadoFederativo,
            @NotBlank(message = "El código postal es obligatorio") @Size(max = 10) String codigoPostal,
            @NotBlank(message = "El país es obligatorio") @Size(max = 50) String pais, double latitud, double longitud,
            @NotNull(message = "El precio por noche es obligatorio") @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0") BigDecimal precioNoche,
            @Min(value = 1, message = "La capacidad mínima es de 1 persona") int capacidad,
            @Min(value = 0, message = "El número de habitaciones no puede ser negativo") int numHabitaciones,
            @Min(value = 0, message = "El número de baños no puede ser negativo") int numBanos,
            @Min(value = 0, message = "Los metros cuadrados no pueden ser negativos") int metrosCuadrados,
            @Size(max = 500) String comodidades, @Size(max = 500) String reglas,
            @NotNull(message = "El estado de la propiedad es obligatorio") EstadoPropiedad estado,
            LocalDateTime fechaRegistro, LocalDateTime fechaActualizacion) {
        this.idPropiedad = idPropiedad;
        this.propietario = propietario;
        this.tipo = tipo;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.estadoFederativo = estadoFederativo;
        this.codigoPostal = codigoPostal;
        this.pais = pais;
        this.latitud = latitud;
        this.longitud = longitud;
        this.precioNoche = precioNoche;
        this.capacidad = capacidad;
        this.numHabitaciones = numHabitaciones;
        this.numBanos = numBanos;
        this.metrosCuadrados = metrosCuadrados;
        this.comodidades = comodidades;
        this.reglas = reglas;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
        this.fechaActualizacion = fechaActualizacion;
    }

    //Constructor sin id_propiedad, fechaRegistro y fecha_actualizacion
    public PropiedadModel(@NotNull(message = "El propietario es obligatorio") ClienteModel propietario,
            @NotBlank(message = "El tipo de propiedad es obligatorio") @Size(max = 40) String tipo,
            @NotBlank(message = "El título es obligatorio") @Size(max = 100) String titulo,
            @NotBlank(message = "La descripción es obligatoria") @Size(max = 500) String descripcion,
            @NotBlank(message = "La dirección es obligatoria") @Size(max = 150) String direccion,
            @NotBlank(message = "La ciudad es obligatoria") @Size(max = 50) String ciudad,
            @NotBlank(message = "El estado o entidad federativa es obligatorio") @Size(max = 50) String estadoFederativo,
            @NotBlank(message = "El código postal es obligatorio") @Size(max = 10) String codigoPostal,
            @NotBlank(message = "El país es obligatorio") @Size(max = 50) String pais, double latitud, double longitud,
            @NotNull(message = "El precio por noche es obligatorio") @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0") BigDecimal precioNoche,
            @Min(value = 1, message = "La capacidad mínima es de 1 persona") int capacidad,
            @Min(value = 0, message = "El número de habitaciones no puede ser negativo") int numHabitaciones,
            @Min(value = 0, message = "El número de baños no puede ser negativo") int numBanos,
            @Min(value = 0, message = "Los metros cuadrados no pueden ser negativos") int metrosCuadrados,
            @Size(max = 500) String comodidades, @Size(max = 500) String reglas,
            @NotNull(message = "El estado de la propiedad es obligatorio") EstadoPropiedad estado) {
        this.propietario = propietario;
        this.tipo = tipo;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.estadoFederativo = estadoFederativo;
        this.codigoPostal = codigoPostal;
        this.pais = pais;
        this.latitud = latitud;
        this.longitud = longitud;
        this.precioNoche = precioNoche;
        this.capacidad = capacidad;
        this.numHabitaciones = numHabitaciones;
        this.numBanos = numBanos;
        this.metrosCuadrados = metrosCuadrados;
        this.comodidades = comodidades;
        this.reglas = reglas;
        this.estado = estado;
    }

    public Long getIdPropiedad() {
        return idPropiedad;
    }

    public void setIdPropiedad(Long idPropiedad) {
        this.idPropiedad = idPropiedad;
    }

    public ClienteModel getPropietario() {
        return propietario;
    }

    public void setPropietario(ClienteModel propietario) {
        this.propietario = propietario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEstadoFederativo() {
        return estadoFederativo;
    }

    public void setEstadoFederativo(String estadoFederativo) {
        this.estadoFederativo = estadoFederativo;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public BigDecimal getPrecioNoche() {
        return precioNoche;
    }

    public void setPrecioNoche(BigDecimal precioNoche) {
        this.precioNoche = precioNoche;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public int getNumHabitaciones() {
        return numHabitaciones;
    }

    public void setNumHabitaciones(int numHabitaciones) {
        this.numHabitaciones = numHabitaciones;
    }

    public int getNumBanos() {
        return numBanos;
    }

    public void setNumBanos(int numBanos) {
        this.numBanos = numBanos;
    }

    public int getMetrosCuadrados() {
        return metrosCuadrados;
    }

    public void setMetrosCuadrados(int metrosCuadrados) {
        this.metrosCuadrados = metrosCuadrados;
    }

    public String getComodidades() {
        return comodidades;
    }

    public void setComodidades(String comodidades) {
        this.comodidades = comodidades;
    }

    public String getReglas() {
        return reglas;
    }

    public void setReglas(String reglas) {
        this.reglas = reglas;
    }

    public EstadoPropiedad getEstado() {
        return estado;
    }

    public void setEstado(EstadoPropiedad estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    
}


