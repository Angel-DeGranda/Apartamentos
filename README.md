# API y Sistema de Gestión de Alquileres — Apartamentos

API REST y panel web para administrar propiedades en alquiler, tanto **pisos completos** como **habitaciones**. Permite gestionar clientes, propiedades, disponibilidad de calendario, reservas, pagos y reportes financieros.

## Descripción de la API

| Módulo | Ruta REST | Qué hace |
|---|---|---|
| **Clientes** | `/api/clientes` | Administra el registro, consulta y actualización de los usuarios del sistema, clasificados como `PROPIETARIO` o `ARRENDATARIO`. |
| **Propiedades** | `/api/propiedades` | Gestiona el catálogo de inmuebles (`PISO` y `HABITACION`), asociándolos a un propietario y definiendo su tarifa por noche, capacidad y características. |
| **Disponibilidad** | `/api/disponibilidades` | Controla el calendario de cada inmueble: permite registrar bloqueos de fechas y tarifas especiales por temporada. |
| **Reservas** | `/api/reservas` | Procesa la creación y cancelación de alquileres, validando que no existan empalmes de fechas ni conflictos de agenda. |
| **Pagos** | `/api/pagos` | Registra cobros, abonos y métodos de pago (`EFECTIVO`, `TARJETA`, `TRANSFERENCIA`) de cada reserva, y controla su estado (`PENDIENTE`, `PAGADO`, `ATRASADO`, `CANCELADO`). |
| **Reportes** | `/api/reportes` | Genera métricas del negocio: ocupación por inmueble, ingresos liquidados y listado de clientes deudores. |

Además de la API, el proyecto incluye un panel web con Thymeleaf y Bootstrap 5 para usar estas funciones desde el navegador.

## Tecnologías utilizadas

- Java 21
- Spring Boot (Spring Web para los endpoints REST)
- Spring Data JPA con Hibernate
- MySQL 8.x y su driver JDBC
- Thymeleaf y Bootstrap 5 para las vistas
- Maven, con Maven Wrapper (`mvnw`) para ejecutar sin instalación global

## Estructura del proyecto

```
src/main/java/com/blue/apartamentos
├── controllers    # Controladores REST (/api/...) y vistas Thymeleaf (/vista/...)
├── models         # Entidades JPA, modelos de datos y reportes
├── repositories   # Interfaces de acceso a datos (Spring Data JPA)
└── services       # Lógica de negocio y manejo de excepciones
src/main/resources
├── application.properties
└── templates      # Vistas Thymeleaf
```

## Instalación y ejecución

### Requisitos previos

- JDK 21 o superior
- MySQL 8.x instalado y en ejecución

### Pasos

1. **Clona el repositorio** y entra a la carpeta del proyecto:

   ```bash
   git clone https://github.com/Angel-DeGranda/Apartamentos.git
   cd Apartamentos
   ```

2. **Crea la base de datos** en MySQL (Workbench, phpMyAdmin o terminal):

   ```sql
   CREATE DATABASE IF NOT EXISTS apartamentos_blue CHARACTER SET utf8mb4;
   ```

3. **Configura las credenciales** de tu MySQL local editando directamente el archivo `src/main/resources/application.properties` (ver la siguiente sección).

4. **Ejecuta la aplicación** desde la raíz del proyecto:

   macOS, Linux y Git Bash (Windows):

   ```bash
   ./mvnw spring-boot:run
   ```

   Windows (PowerShell):

   ```powershell
   .\mvnw spring-boot:run
   ```

   Windows (Símbolo del sistema / CMD):

   ```bat
   mvnw spring-boot:run
   ```

   Puedes ejecutar la clase principal `ApartamentosApplication.java` directamente desde tu IDE.

5. **Verifica** que funciona: abre <http://localhost:8080/vista/clientes> para usar la interfaz web, o haz peticiones HTTP desde Postman a <http://localhost:8080/api/clientes>.

## Variables de entorno y configuración

El proyecto no usa variables de entorno ni archivos `.env`: toda la configuración está en `src/main/resources/application.properties`. **Para ejecutarlo en tu equipo, cambia directamente en ese archivo el usuario y la contraseña de la base de datos por los de tu propio MySQL local.**

```properties
spring.application.name=apartamentos

spring.datasource.url=jdbc:mysql://localhost:3306/apartamentos_blue
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.properties.hibernate.format_sql=true

server.port=8080
```

| Propiedad | Para qué sirve | Qué hacer |
|---|---|---|
| `spring.datasource.url` | Dirección de la base de datos | Déjala igual si tu MySQL corre en `localhost:3306` y creaste `apartamentos_blue` |
| `spring.datasource.username` | Usuario de MySQL | **Cámbialo por el tuyo** |
| `spring.datasource.password` | Contraseña de MySQL | **Cámbiala por la tuya** |
| `spring.jpa.hibernate.ddl-auto` | Crea o actualiza las tablas automáticamente al arrancar | Déjala en `update` |
| `spring.jpa.show-sql` | Muestra en consola las consultas SQL | Opcional |
| `server.port` | Puerto de la aplicación | `8080`; cámbialo solo si está ocupado |

Al ejecutar con `ddl-auto=update`, Hibernate crea las tablas por ti, pero la base de datos `apartamentos_blue` debe existir (paso 2).

## Endpoints

### Clientes (`/api/clientes`)

| Método | Ruta | Descripción | Respuestas |
|---|---|---|---|
| GET | `/api/clientes` | Lista los clientes | 200 |
| GET | `/api/clientes/{id}` | Obtiene un cliente por id | 200, 404 |
| POST | `/api/clientes` | Registra un cliente | 201, 400 |
| PUT | `/api/clientes/{id}` | Actualiza un cliente | 200, 400, 404 |
| DELETE | `/api/clientes/{id}` | Elimina un cliente | 204, 404, 409 (tiene propiedades o reservas) |

### Propiedades (`/api/propiedades`)

| Método | Ruta | Descripción | Respuestas |
|---|---|---|---|
| GET | `/api/propiedades` | Lista las propiedades | 200 |
| GET | `/api/propiedades/{id}` | Obtiene una propiedad por id | 200, 404 |
| GET | `/api/propiedades/tipo/{tipo}` | Lista las propiedades de un tipo (`PISO` o `HABITACION`) | 200 |
| GET | `/api/propiedades/propietario/{idPropietario}` | Lista las propiedades de un propietario | 200 |
| POST | `/api/propiedades` | Registra una propiedad | 201, 400, 404 |
| PUT | `/api/propiedades/{id}` | Actualiza una propiedad | 200, 400, 404 |
| DELETE | `/api/propiedades/{id}` | Elimina una propiedad | 204, 404, 409 (tiene información vinculada) |

### Disponibilidad (`/api/disponibilidades`)

| Método | Ruta | Descripción | Respuestas |
|---|---|---|---|
| GET | `/api/disponibilidades/propiedad/{idPropiedad}` | Lista el calendario de una propiedad | 200 |
| POST | `/api/disponibilidades` | Registra un bloqueo de fechas o una tarifa especial | 201, 400, 404 |
| DELETE | `/api/disponibilidades/{id}` | Elimina un registro de disponibilidad | 204, 404 |

### Reservas (`/api/reservas`)

| Método | Ruta | Descripción | Respuestas |
|---|---|---|---|
| GET | `/api/reservas` | Lista las reservas | 200 |
| GET | `/api/reservas/{id}` | Obtiene una reserva por id | 200, 404 |
| POST | `/api/reservas` | Crea una reserva | 201, 400, 404, 409 |
| PUT | `/api/reservas/{id}/cancelar` | Cancela una reserva | 200, 400, 404 |

### Pagos (`/api/pagos`)

| Método | Ruta | Descripción | Respuestas |
|---|---|---|---|
| GET | `/api/pagos` | Lista los pagos | 200 |
| GET | `/api/pagos/{id}` | Obtiene un pago por id | 200, 404 |
| POST | `/api/pagos` | Registra un pago | 201, 400, 404 |
| PUT | `/api/pagos/{id}/estado?estado={estado}` | Cambia el estado de un pago (`PENDIENTE`, `PAGADO`, `ATRASADO` o `CANCELADO`) | 200, 400, 404 |

### Reportes (`/api/reportes`)

| Método | Ruta | Descripción | Respuestas |
|---|---|---|---|
| GET | `/api/reportes/ocupacion?fechaInicio=AAAA-MM-DD&fechaFin=AAAA-MM-DD` | Ocupación por inmueble en el rango de fechas | 200, 400 |
| GET | `/api/reportes/ingresos?fechaInicio=AAAA-MM-DD&fechaFin=AAAA-MM-DD` | Ingresos liquidados en el rango de fechas | 200, 400 |
| GET | `/api/reportes/deudores` | Listado de pagos pendientes de clientes deudores | 200 |

### Vistas web

| Sección | URL local |
|---|---|
| Clientes (también en la raíz `/`) | <http://localhost:8080/vista/clientes> |
| Propiedades | <http://localhost:8080/vista/propiedades> |
| Disponibilidad | <http://localhost:8080/vista/disponibilidades> |
| Reservas | <http://localhost:8080/vista/reservas> |
| Pagos | <http://localhost:8080/vista/pagos> |
| Reportes | <http://localhost:8080/vista/reportes> |

### Códigos de respuesta

Los controladores REST gestionan los errores con `@ExceptionHandler`. Cuando hay un error, el cuerpo de la respuesta es el **mensaje en texto plano**.

| Código | Significado |
|---|---|
| 200 | Consulta o actualización realizada correctamente |
| 201 | Registro creado correctamente |
| 204 | Eliminación realizada, sin contenido en la respuesta |
| 400 | Regla de negocio incumplida o datos inválidos (`IllegalArgumentException`), o parámetros de consulta faltantes o con formato incorrecto |
| 404 | Recurso no encontrado por id (`NoSuchElementException`) |
| 409 | Conflicto de integridad referencial o clave foránea (`DataIntegrityViolationException`), por ejemplo al eliminar un cliente o una propiedad con información vinculada |

## Ejemplos de peticiones y respuestas

Los ejemplos se muestran como en Postman: método y URL, y el JSON que se pega en la pestaña **Body**. En las peticiones `POST` y `PUT` con cuerpo, selecciona **Body → raw → JSON**; Postman agrega el encabezado `Content-Type` automáticamente.

### Registrar un cliente propietario

**POST** `http://localhost:8080/api/clientes`

En **Body → raw → JSON**:

```json
{
  "nombres": "Carlos",
  "apellidos": "Mendoza",
  "email": "carlos.mendoza@email.com",
  "telefono": "5551234567",
  "fechaNacimiento": "1985-05-20",
  "ine": "INE1234567890",
  "direccion": "Av. Reforma 100, CDMX",
  "estatus": "ACTIVO",
  "tipo": "PROPIETARIO",
  "password": "password123"
}
```

Respuesta `201 Created`:

```json
{
  "id": 1,
  "nombres": "Carlos",
  "apellidos": "Mendoza",
  "email": "carlos.mendoza@email.com",
  "telefono": "5551234567",
  "fechaNacimiento": "1985-05-20",
  "ine": "INE1234567890",
  "direccion": "Av. Reforma 100, CDMX",
  "estatus": "ACTIVO",
  "tipo": "PROPIETARIO",
  "fechaRegistro": "2026-10-11T18:10:06.1617668"
}
```

### Registrar una propiedad

La propiedad se asocia a su propietario indicando su `id`:

**POST** `http://localhost:8080/api/propiedades`

En **Body → raw → JSON**:

```json
{
  "propietario": { "id": 1 },
  "tipo": "PISO",
  "titulo": "Departamento de Lujo en Condesa",
  "descripcion": "Piso completo de 2 habitaciones totalmente amueblado",
  "direccion": "Av. Ámsterdam 123, Depto 301",
  "ciudad": "Ciudad de México",
  "estadoFederativo": "CDMX",
  "pais": "México",
  "codigoPostal": "06100",
  "capacidad": 4,
  "precioNoche": 1500.00,
  "comodidades": "WiFi, A/C, Cocina equipada",
  "numHabitaciones": 2,
  "numBanos": 2,
  "metrosCuadrados": 85,
  "estado": "DISPONIBLE"
}
```

Respuesta `201 Created`:

```json
{
  "idPropiedad": 1,
  "tipo": "PISO",
  "titulo": "Departamento de Lujo en Condesa",
  "descripcion": "Piso completo de 2 habitaciones totalmente amueblado",
  "direccion": "Av. Ámsterdam 123, Depto 301",
  "ciudad": "Ciudad de México",
  "estadoFederativo": "CDMX",
  "pais": "México",
  "codigoPostal": "06100",
  "latitud": 0.0,
  "longitud": 0.0,
  "capacidad": 4,
  "precioNoche": 1500.00,
  "comodidades": "WiFi, A/C, Cocina equipada",
  "numHabitaciones": 2,
  "numBanos": 2,
  "metrosCuadrados": 85,
  "estado": "DISPONIBLE",
  "fechaRegistro": "2026-10-11T18:13:05.2539500",
  "fechaActualizacion": "2026-10-11T18:13:05.2539500",
  "propietario": { "id": 1, "nombres": "Carlos", "tipo": "PROPIETARIO", "...": "..." }
}
```

### Bloquear fechas con una tarifa especial

**POST** `http://localhost:8080/api/disponibilidades`

En **Body → raw → JSON**:

```json
{
  "propiedad": { "idPropiedad": 1 },
  "fechaInicio": "2026-12-20",
  "fechaFin": "2026-12-25",
  "disponible": false,
  "precioEspecial": 2000.00
}
```

Respuesta `201 Created`:

```json
{
  "idDisponibilidad": 1,
  "fechaInicio": "2026-12-20",
  "fechaFin": "2026-12-25",
  "disponible": false,
  "precioEspecial": 2000.00,
  "fechaRegistro": "2026-10-11T18:13:40.5015722",
  "propiedad": { "idPropiedad": 1, "titulo": "Departamento de Lujo en Condesa", "...": "..." }
}
```

### Crear una reserva

Se indica el arrendatario (cliente de tipo `ARRENDATARIO`) y la propiedad:

**POST** `http://localhost:8080/api/reservas`

En **Body → raw → JSON**:

```json
{
  "arrendatario": { "id": 2 },
  "propiedad": { "idPropiedad": 1 },
  "fechaInicio": "2026-11-01",
  "fechaFin": "2026-11-05",
  "precioTotal": 6000.00,
  "estado": "PENDIENTE"
}
```

Respuesta `201 Created`:

```json
{
  "idReserva": 1,
  "arrendatario": { "id": 2, "nombres": "Ana", "apellidos": "Gómez", "tipo": "ARRENDATARIO", "...": "..." },
  "propiedad": { "idPropiedad": 1, "titulo": "Departamento de Lujo en Condesa", "...": "..." },
  "fechaInicio": "2026-11-01",
  "fechaFin": "2026-11-05",
  "precioTotal": 6000.00,
  "estado": "PENDIENTE",
  "fechaCreacion": "2026-10-11T18:14:10.396656"
}
```

### Registrar un pago

**POST** `http://localhost:8080/api/pagos`

En **Body → raw → JSON**:

```json
{
  "reserva": { "idReserva": 1 },
  "monto": 6000.00,
  "fechaVencimiento": "2026-10-30",
  "fechaPago": "2026-10-28",
  "metodoPago": "TRANSFERENCIA",
  "estado": "PAGADO"
}
```

Respuesta `201 Created`:

```json
{
  "idPago": 1,
  "reserva": { "idReserva": 1, "estado": "PENDIENTE", "...": "..." },
  "monto": 6000.00,
  "fechaVencimiento": "2026-10-30",
  "fechaPago": "2026-10-28",
  "metodoPago": "TRANSFERENCIA",
  "estado": "PAGADO",
  "fechaRegistro": "2026-10-11T18:14:35.598511"
}
```

### Actualizar un cliente

**PUT** `http://localhost:8080/api/clientes/2`

En **Body → raw → JSON**:

```json
{
  "nombres": "Ana Sofía",
  "apellidos": "Gómez Juárez",
  "email": "ana.gomez@email.com",
  "telefono": "5550001122",
  "fechaNacimiento": "1992-11-15",
  "ine": "INE0987654321",
  "direccion": "Nueva Calle 123, CDMX",
  "estatus": "ACTIVO",
  "tipo": "ARRENDATARIO",
  "password": "password123"
}
```

Respuesta `200 OK`: el cliente con los datos actualizados.

```json
{
  "id": 2,
  "nombres": "Ana Sofía",
  "apellidos": "Gómez Juárez",
  "email": "ana.gomez@email.com",
  "telefono": "5550001122",
  "fechaNacimiento": "1992-11-15",
  "ine": "INE0987654321",
  "direccion": "Nueva Calle 123, CDMX",
  "estatus": "ACTIVO",
  "tipo": "ARRENDATARIO",
  "fechaRegistro": "2026-10-11T18:13:01.756486"
}
```

### Actualizar una propiedad

**PUT** `http://localhost:8080/api/propiedades/1`

En **Body → raw → JSON**:

```json
{
  "propietario": { "id": 1 },
  "tipo": "PISO",
  "titulo": "Departamento de Lujo en Condesa",
  "descripcion": "Piso completo amueblado con balcón",
  "direccion": "Av. Ámsterdam 123, Depto 301",
  "ciudad": "Ciudad de México",
  "estadoFederativo": "CDMX",
  "pais": "México",
  "codigoPostal": "06100",
  "capacidad": 4,
  "precioNoche": 1500.00,
  "comodidades": "WiFi, A/C, Cocina equipada",
  "numHabitaciones": 2,
  "numBanos": 2,
  "metrosCuadrados": 85,
  "estado": "DISPONIBLE"
}
```

Respuesta `200 OK`: la propiedad con la descripción actualizada y una nueva `fechaActualizacion`.

```json
{
  "idPropiedad": 1,
  "tipo": "PISO",
  "titulo": "Departamento de Lujo en Condesa",
  "descripcion": "Piso completo amueblado con balcón",
  "estado": "DISPONIBLE",
  "fechaRegistro": "2026-10-11T18:13:05.253951",
  "fechaActualizacion": "2026-10-11T18:17:35.4692665",
  "...": "..."
}
```

### Consultar propiedades y disponibilidad

- Propiedades por tipo (`PISO` o `HABITACION`): **GET** `http://localhost:8080/api/propiedades/tipo/PISO`
- Propiedades de un propietario: **GET** `http://localhost:8080/api/propiedades/propietario/1`
- Calendario de una propiedad: **GET** `http://localhost:8080/api/disponibilidades/propiedad/1`

Ninguna lleva cuerpo.

Respuesta `200 OK` del calendario: una lista de registros de disponibilidad.

```json
[
  {
    "idDisponibilidad": 1,
    "fechaInicio": "2026-12-20",
    "fechaFin": "2026-12-25",
    "disponible": false,
    "precioEspecial": 2000.00,
    "propiedad": { "idPropiedad": 1, "...": "..." }
  }
]
```

### Cancelar una reserva

**PUT** `http://localhost:8080/api/reservas/1/cancelar`

Sin cuerpo.

Respuesta `200 OK`: la reserva con su estado actualizado.

### Cambiar el estado de un pago

El estado se envía como parámetro de la URL, no en el cuerpo:

**PUT** `http://localhost:8080/api/pagos/1/estado?estado=PAGADO`

Sin cuerpo.

Respuesta `200 OK`: el pago con el nuevo estado.

```json
{
  "idPago": 1,
  "estado": "PAGADO",
  "...": "..."
}
```

### Consultar reportes

Los reportes de ocupación e ingresos reciben el rango de fechas como parámetros; el de deudores no necesita parámetros:

- Ocupación: **GET** `http://localhost:8080/api/reportes/ocupacion?fechaInicio=2026-11-01&fechaFin=2026-11-30`
- Ingresos: **GET** `http://localhost:8080/api/reportes/ingresos?fechaInicio=2026-11-01&fechaFin=2026-11-30`
- Deudores: **GET** `http://localhost:8080/api/reportes/deudores`

Ninguna lleva cuerpo. En Postman, las fechas también se pueden capturar en la pestaña **Params** (`fechaInicio` y `fechaFin`).

Si falta un parámetro o la fecha no tiene el formato `AAAA-MM-DD`, la respuesta es `400 Bad Request`.

### Eliminar un registro de disponibilidad

**DELETE** `http://localhost:8080/api/disponibilidades/1`

Sin cuerpo.

Respuesta `204 No Content`, sin cuerpo.

### Error al eliminar un cliente con registros asociados

**DELETE** `http://localhost:8080/api/clientes/1`

Sin cuerpo.

Respuesta `409 Conflict`, con el mensaje en texto plano:

```text
No se puede eliminar el cliente porque tiene propiedades o reservas asociadas.
```