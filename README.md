# Sistema de Gestión de Rentas e Inmuebles — Apartamentos API

API REST desarrollada con **Java y Spring Boot** para administrar el alquiler de pisos completos y habitaciones. Permite gestionar clientes (propietarios y arrendatarios), propiedades, disponibilidades, reservas, pagos y reportes de ocupación e ingresos.

El proyecto busca centralizar la información y reducir la dependencia de procesos manuales y hojas de cálculo.

## Tecnologías utilizadas

- **Java:** 17 o superior.
- **Spring Boot:** 3.x.
- **Spring Web:** creación de endpoints REST.
- **Spring Data JPA:** persistencia y acceso a datos.
- **Jakarta Validation:** validación de datos de entrada.
- **MySQL:** 8.x.
- **Hibernate:** ORM utilizado por JPA.
- **Apache Maven:** administración de dependencias y compilación.

## Requisitos previos

Antes de ejecutar la API, instala o verifica lo siguiente:

- JDK 17 o superior.
- MySQL Server 8.x en ejecución.
- Maven 3.8 o superior, o el Maven Wrapper incluido en el proyecto (`./mvnw` o `mvnw.cmd`).

## Configuración segura

### 1. Configurar `application.properties`

En `src/main/resources/application.properties`, utiliza propiedades como las siguientes:

```properties
spring.application.name=apartamentos

spring.datasource.url=${TU_URL_DE_LA_BASE_DE_DATOS_AQUI}
spring.datasource.username=${TU_USUARIO_AQUI}
spring.datasource.password=${TU_CONTRASENIA_AQUI}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
server.port=${TU_PUERTO_DE_SERVIDOR_AQUI}
```

Estas líneas contienen referencias a variables de entorno, no credenciales; deben definirse antes de iniciar la aplicación, si no se inician se tomarán las predefinidas que son:

```properties
spring.application.name=apartamentos

spring.datasource.url=jdbc:mysql://localhost:3306/apartamentos_blue
spring.datasource.username=root
spring.datasource.password=root

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
server.port=8080
```

En caso de que cuentes con otros datos, cambialos segun tu base de datos.

> **Nota:** `spring.jpa.hibernate.ddl-auto=update` permite que Hibernate actualice el esquema automáticamente. Para producción, evalúa una estrategia de migraciones controladas y realiza respaldos de la base de datos. Evita activar el registro de SQL si puede revelar datos sensibles.

### 2. Crear la base de datos

Conéctate a MySQL y ejecuta:

```sql
CREATE DATABASE IF NOT EXISTS apartamentos_blue;
```

Este comando crea la base de datos, pero no crea un usuario ni establece una contraseña. Utiliza las credenciales de una cuenta de MySQL configurada en tu entorno.

### 3. Definir las variables de entorno

Los siguientes ejemplos son para una máquina de desarrollo local. Sustituye los valores de usuario y contraseña por los que realmente hayas configurado en tu MySQL. No publiques contraseñas reales.

**Linux / macOS (Bash):**

```bash
export SPRING_DATASOURCE_URL="jdbc:mysql://localhost:3306/apartamentos_blue"
export SPRING_DATASOURCE_USERNAME="tu_usuario_mysql"
export SPRING_DATASOURCE_PASSWORD="tu_contraseña_mysql"
export SERVER_PORT="8080"
```

**Windows (PowerShell):**

```powershell
$env:SPRING_DATASOURCE_URL = "jdbc:mysql://localhost:3306/apartamentos_blue"
$env:SPRING_DATASOURCE_USERNAME = "tu_usuario_mysql"
$env:SPRING_DATASOURCE_PASSWORD = "tu_contraseña_mysql"
$env:SERVER_PORT = "8080"
```

Estas variables se aplican a la sesión actual de terminal. Si abres otra terminal, tendrás que configurarlas de nuevo.

### Variables de entorno necesarias

| Variable | Descripción | Ejemplo no secreto |
|---|---|---|
| `SPRING_DATASOURCE_URL` | URL de conexión a MySQL | `jdbc:mysql://localhost:3306/apartamentos_blue` |
| `SPRING_DATASOURCE_USERNAME` | Usuario de MySQL | `tu_usuario_mysql` |
| `SPRING_DATASOURCE_PASSWORD` | Contraseña del usuario de MySQL | No se proporciona un valor real |
| `SERVER_PORT` | Puerto HTTP de la API | `8080` |

`SERVER_PORT` es opcional porque la configuración anterior usa `8080` como valor predeterminado. Las tres variables de conexión a la base de datos sí deben estar definidas.

## Instalación y ejecución

### 1. Clonar el repositorio

Sustituye la URL por la dirección real del repositorio:

```bash
git clone https://github.com/Angel-DeGranda/Apartamentos.git
cd Apartamentos
```

### 2. Crear la base de datos

Ejecuta en MySQL:

```sql
CREATE DATABASE IF NOT EXISTS apartamentos_blue;
```

### 3. Configurar las variables de entorno

Sigue las instrucciones de la sección [Configuración segura](#configuración-segura) correspondientes a tu sistema operativo. Verifica que las variables estén definidas en la misma terminal desde la que ejecutarás la API.

### 4. Compilar el proyecto

**Linux / macOS:**

```bash
./mvnw clean package
```

**Windows:**

```powershell
.\mvnw.cmd clean package
```

Si el proyecto no incluye Maven Wrapper, utiliza `mvn clean package` si Maven está instalado. Para compilar y ejecutar las pruebas, no agregues `-DskipTests`; úsalo únicamente si tienes una razón concreta para omitirlas.

### 5. Ejecutar la aplicación

**Linux / macOS:**

```bash
./mvnw spring-boot:run
```

**Windows:**

```powershell
.\mvnw.cmd spring-boot:run
```

Si el inicio termina correctamente, la API estará disponible en:

[http://localhost:8080](http://localhost:8080)

## Listado de endpoints

Los siguientes endpoints describen las operaciones disponibles en la API. Los nombres de campos, parámetros y códigos de respuesta definitivos dependen de la implementación del proyecto.

### Clientes — `/api/clientes`

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/api/clientes` | Listar todos los clientes. |
| `GET` | `/api/clientes/{id}` | Obtener un cliente por ID. |
| `GET` | `/api/clientes/tipo/{tipo}` | Filtrar por tipo: `PROPIETARIO` o `ARRENDATARIO`. |
| `POST` | `/api/clientes` | Registrar un cliente. |
| `PUT` | `/api/clientes/{id}` | Actualizar un cliente. |
| `DELETE` | `/api/clientes/{id}` | Eliminar un cliente. |

### Propiedades — `/api/propiedades`

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/api/propiedades` | Listar pisos y habitaciones. |
| `GET` | `/api/propiedades/{id}` | Consultar una propiedad por ID. |
| `GET` | `/api/propiedades/propietario/{idPropietario}` | Listar propiedades de un propietario. |
| `GET` | `/api/propiedades/tipo/{tipo}` | Filtrar propiedades por tipo (`PISO` o `HABITACION`). |
| `POST` | `/api/propiedades` | Registrar un piso o una habitación. |
| `PUT` | `/api/propiedades/{id}` | Actualizar una propiedad. |
| `DELETE` | `/api/propiedades/{id}` | Eliminar una propiedad. |

### Disponibilidades — `/api/disponibilidades`

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/api/disponibilidades/propiedad/{idPropiedad}` | Consultar disponibilidades o bloqueos de una propiedad. |
| `POST` | `/api/disponibilidades` | Registrar un rango de disponibilidad o un precio especial. |
| `DELETE` | `/api/disponibilidades/{id}` | Eliminar un rango de disponibilidad. |

### Reservas — `/api/reservas`

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/api/reservas` | Listar todas las reservas. |
| `GET` | `/api/reservas/{id}` | Consultar una reserva por ID. |
| `GET` | `/api/reservas/arrendatario/{idArrendatario}` | Consultar las reservas de un arrendatario. |
| `POST` | `/api/reservas` | Crear una reserva. |
| `PUT` | `/api/reservas/{id}/cancelar` | Cancelar una reserva activa. |

### Pagos — `/api/pagos`

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/api/pagos` | Listar todos los pagos. |
| `GET` | `/api/pagos/reserva/{idReserva}` | Consultar pagos y abonos de una reserva. |
| `POST` | `/api/pagos` | Registrar un abono o una liquidación. |
| `PUT` | `/api/pagos/{id}/estado?nuevoEstado={ESTADO}` | Actualizar el estado: `PENDIENTE`, `PAGADO`, `ATRASADO` o `CANCELADO`. |

### Reportes — `/api/reportes`

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/api/reportes/ocupacion?fechaInicio=YYYY-MM-DD&fechaFin=YYYY-MM-DD` | Consultar la ocupación por propiedad en un período. |
| `GET` | `/api/reportes/ingresos?fechaInicio=YYYY-MM-DD&fechaFin=YYYY-MM-DD` | Consultar los ingresos liquidados en un período. |
| `GET` | `/api/reportes/deudores` | Consultar morosidad y cuentas por cobrar. |

## Ejemplos de peticiones y respuestas

Los siguientes ejemplos ilustran el formato esperado de las peticiones y respuestas. Los datos son ficticios y las respuestas pueden variar según la implementación real.

### 1. Registrar un cliente

**Petición — `POST /api/clientes`**

```http
POST /api/clientes
Content-Type: application/json
```

```json
{
  "tipo": "ARRENDATARIO",
  "nombres": "Ana",
  "apellidos": "Gómez",
  "email": "ana.gomez@example.com",
  "telefono": "5559876543",
  "fechaNacimiento": "1992-11-15",
  "ine": "INE0987654321",
  "direccion": "Calle Insurgentes 50, CDMX",
  "estatus": "ACTIVO",
  "password": "CambiarPorUnaClaveDePrueba"
}
```

**Respuesta ilustrativa — `201 Created`**

```json
{
  "id": 2,
  "tipo": "ARRENDATARIO",
  "nombres": "Ana",
  "apellidos": "Gómez",
  "email": "ana.gomez@example.com",
  "telefono": "5559876543",
  "fechaNacimiento": "1992-11-15",
  "ine": "INE0987654321",
  "direccion": "Calle Insurgentes 50, CDMX",
  "fechaRegistro": "2026-10-11T18:00:00",
  "estatus": "ACTIVO"
}
```

> En una implementación real, la contraseña nunca debe devolverse en la respuesta. Debe almacenarse mediante un hash seguro y no como texto plano.

### 2. Crear una reserva

**Petición — `POST /api/reservas`**

```http
POST /api/reservas
Content-Type: application/json
```

```json
{
  "arrendatario": {
    "id": 2
  },
  "propiedad": {
    "idPropiedad": 1
  },
  "fechaInicio": "2026-11-01",
  "fechaFin": "2026-11-05",
  "precioTotal": 6000.00
}
```

**Respuesta ilustrativa — `201 Created`**

```json
{
  "idReserva": 1,
  "arrendatario": {
    "id": 2,
    "tipo": "ARRENDATARIO",
    "nombres": "Ana",
    "apellidos": "Gómez",
    "email": "ana.gomez@example.com"
  },
  "propiedad": {
    "idPropiedad": 1,
    "titulo": "Departamento de ejemplo en Condesa",
    "tipo": "PISO"
  },
  "fechaInicio": "2026-11-01",
  "fechaFin": "2026-11-05",
  "precioTotal": 6000.00,
  "estado": "PENDIENTE",
  "fechaCreacion": "2026-10-11T18:05:00"
}
```

### 3. Consultar el reporte de deudores

**Petición — `GET /api/reportes/deudores`**

```http
GET /api/reportes/deudores
```

**Respuesta ilustrativa — `200 OK`**

```json
[
  {
    "idPago": 3,
    "idReserva": 2,
    "nombreCliente": "Carlos Mendoza",
    "emailCliente": "carlos.mendoza@example.com",
    "telefonoCliente": "5551234567",
    "montoPendiente": 1800.00,
    "fechaVencimiento": "2026-10-01"
  }
]
```

### 4. Error de validación

**Respuesta ilustrativa — `400 Bad Request`**

Por ejemplo, si se envía un correo duplicado o fechas no válidas, la API podría devolver un error con una estructura similar a esta, siempre que el manejo de errores esté implementado de esa manera:

```json
{
  "timestamp": "2026-10-11T18:10:00",
  "status": 400,
  "error": "Petición Incorrecta",
  "mensaje": "El correo electrónico ya se encuentra registrado"
}
```

---

## Interfaz Web (Navegación con Thymeleaf)

Además de los endpoints de la API REST, el sistema cuenta con un panel web interactivo desarrollado con **Thymeleaf** y **Bootstrap 5** para la gestión visual del sistema.

### Acceso a las Vistas

Al iniciar la aplicación (`http://localhost:8080`), la raíz redirige automáticamente al módulo de clientes. Puedes acceder directamente a cada sección a través de las siguientes URLs:

* **Inicio / Clientes:** `http://localhost:8080/vista/clientes`
  * Alta, consulta, edición y eliminación de propietarios y arrendatarios.
* **Propiedades:** `http://localhost:8080/vista/propiedades`
  * Catálogo general de inmuebles (pisos y habitaciones) con asignación de propietarios.
* **Disponibilidad:** `http://localhost:8080/vista/disponibilidades`
  * Control de fechas bloqueadas y asignación de precios especiales por inmueble.
* **Reservas:** `http://localhost:8080/vista/reservas`
  * Registro de alquileres y cancelación de reservas activas.
* **Pagos:** `http://localhost:8080/vista/pagos`
  * Registro de abonos y control del estado financiero (Pendiente, Pagado, Atrasado, Cancelado).
* **Reportes:** `http://localhost:8080/vista/reportes`
  * Panel gráfico con métricas de ingresos liquidados, porcentaje de ocupación y listado de clientes deudores (morosidad).
