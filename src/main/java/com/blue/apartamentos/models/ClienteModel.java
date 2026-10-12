package com.blue.apartamentos.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "clientes")
public class ClienteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long idCliente;

    @NotNull(message = "El tipo de cliente es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cliente", nullable = false, length = 20)
    private TipoCliente tipo = TipoCliente.ARRENDATARIO;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 35, message = "El nombre no puede exceder 35 caracteres")
    @Column(name = "nombre", nullable = false, length = 35)
    private String nombres;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(max = 35, message = "Los apellidos no pueden exceder 35 caracteres")
    @Column(name = "apellidos", nullable = false, length = 35)
    private String apellidos;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El formato de correo no es válido")
    @Size(max = 50, message = "El email no puede exceder 50 caracteres")
    @Column(name = "email", unique = true, nullable = false, length = 50)
    private String email;

    @Size(max = 15, message = "El teléfono no puede exceder 15 caracteres")
    @Column(name = "telefono", length = 15)
    private String telefono;

    @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Size(max = 20, message = "El INE no puede exceder 20 caracteres")
    @Column(name = "numero_ine", unique = true, length = 20)
    private String ine;

    @Size(max = 100, message = "La dirección no puede exceder 100 caracteres")
    @Column(name = "direccion", length = 100)
    private String direccion;

    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    @NotNull(message = "El estatus es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "estatus", nullable = false)
    private ClienteStatus estatus = ClienteStatus.ACTIVO;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, max = 100, message = "La contraseña debe tener al menos 6 caracteres")
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    // Callbacks
    @PrePersist
    void onCreate() {
        this.fechaRegistro = LocalDateTime.now();
    }

    // Enums
    public enum ClienteStatus {
        ACTIVO,
        INACTIVO,
        SUSPENDIDO
    }

    public enum TipoCliente {
        PROPIETARIO,
        ARRENDATARIO
    }

    

    public Long getId() {
        return idCliente;
    }

    public void setId(Long id) {
        this.idCliente = id;
    }

    public TipoCliente getTipo() {
        return tipo;
    }

    public void setTipo(TipoCliente tipo) {
        this.tipo = tipo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getIne() {
        return ine;
    }

    public void setIne(String ine) {
        this.ine = ine;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public ClienteStatus getEstatus() {
        return estatus;
    }

    public void setEstatus(ClienteStatus estatus) {
        this.estatus = estatus;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ClienteModel() {

    }

    public ClienteModel(Long id, @NotNull(message = "El tipo de cliente es obligatorio") TipoCliente tipo,
            @NotBlank(message = "El nombre es obligatorio") @Size(max = 35, message = "El nombre no puede exceder 35 caracteres") String nombres,
            @NotBlank(message = "Los apellidos son obligatorios") @Size(max = 35, message = "Los apellidos no pueden exceder 35 caracteres") String apellidos,
            @NotBlank(message = "El correo electrónico es obligatorio") @Email(message = "El formato de correo no es válido") @Size(max = 50, message = "El email no puede exceder 50 caracteres") String email,
            @Size(max = 15, message = "El teléfono no puede exceder 15 caracteres") String telefono,
            @Past(message = "La fecha de nacimiento debe ser una fecha pasada") LocalDate fechaNacimiento,
            @Size(max = 20, message = "El INE no puede exceder 20 caracteres") String ine,
            @Size(max = 100, message = "La dirección no puede exceder 100 caracteres") String direccion,
            LocalDateTime fechaRegistro, @NotNull(message = "El estatus es obligatorio") ClienteStatus estatus,
            @NotBlank(message = "La contraseña es obligatoria") @Size(min = 6, max = 100, message = "La contraseña debe tener al menos 6 caracteres") String password) {
        this.idCliente = id;
        this.tipo = tipo;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.ine = ine;
        this.direccion = direccion;
        this.fechaRegistro = fechaRegistro;
        this.estatus = estatus;
        this.password = password;
    }

    // Crea otro constructor con los atributos necesarios para registrar un cliente
    public ClienteModel(String nombres, String apellidos, String email,
            String telefono, LocalDate fechaNacimiento, String ine, String direccion, ClienteStatus estatus,
            String password) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.ine = ine;
        this.direccion = direccion;
        this.fechaRegistro = LocalDateTime.now();
        this.estatus = estatus;
        this.password = password;
    }
}
