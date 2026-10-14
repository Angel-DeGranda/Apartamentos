# API y Sistema de Gestión de Alquileres --- Apartamentos

API REST y panel web interactivo para la administración integral de
propiedades en alquiler, tanto pisos completos como habitaciones. El
sistema permite gestionar clientes, propiedades, disponibilidad de
calendario, reservas, pagos y reportes financieros.

Está desarrollado con **Java 21**, **Spring Boot**, **Spring Data JPA**,
**MySQL** y **Thymeleaf**. La interfaz web utiliza **Bootstrap 5**.

------------------------------------------------------------------------

## Estructura del proyecto

El proyecto utiliza una arquitectura simplificada organizada en cuatro
paquetes principales:

``` text
src/main/java/com/api/apartamentos
├── controllers    # Controladores REST (/api/...) y vistas Thymeleaf (/vista/...)
├── models         # Entidades JPA, modelos de datos y reportes
├── repositories   # Interfaces de acceso a datos (Spring Data JPA)
└── services       # Lógica de negocio y manejo de excepciones

src/main/resources
├── application.properties  # Configuración de la aplicación
└── templates               # Plantillas HTML de Thymeleaf
```

## Tecnologías utilizadas

-   **Java 21**
-   **Spring Boot**
-   **Spring Web** para la creación de endpoints REST.
-   **Spring Data JPA** para la persistencia y el acceso a datos.
-   **Hibernate / JPA** como tecnología ORM.
-   **MySQL 8.x** como base de datos.
-   **MySQL JDBC Driver** para la conexión con la base de datos.
-   **Thymeleaf** para las vistas del panel web.
-   **Bootstrap 5** para el diseño de la interfaz.
-   **Apache Maven** para gestionar dependencias y compilar el proyecto.
-   **Maven Wrapper (`mvnw`)** para ejecutar Maven sin requerir una
    instalación global.

## Descripción de módulos

  -------------------------------------------------------------------------
  Módulo                  Ruta REST                 Descripción
  ----------------------- ------------------------- -----------------------
  Clientes                `/api/clientes`           Administra el registro,
                                                    consulta y
                                                    actualización de
                                                    clientes, clasificados
                                                    como `PROPIETARIO` o
                                                    `ARRENDATARIO`.

  Propiedades             `/api/propiedades`        Gestiona el catálogo de
                                                    inmuebles (`PISO` y
                                                    `HABITACION`), su
                                                    asignación a
                                                    propietarios, precios
                                                    por noche, capacidad y
                                                    características.

  Disponibilidad          `/api/disponibilidades`   Controla el calendario
                                                    por inmueble y permite
                                                    registrar bloqueos de
                                                    fechas y tarifas
                                                    especiales por
                                                    temporada.

  Reservas                `/api/reservas`           Procesa la creación y
                                                    cancelación de
                                                    alquileres, validando
                                                    la disponibilidad de
                                                    fechas y los tipos de
                                                    usuario.

  Pagos                   `/api/pagos`              Registra cobros, abonos
                                                    y métodos de pago
                                                    (`EFECTIVO`, `TARJETA`,
                                                    `TRANSFERENCIA`), y
                                                    gestiona estados
                                                    (`PENDIENTE`, `PAGADO`,
                                                    `ATRASADO`,
                                                    `CANCELADO`).

  Reportes                `/api/reportes`           Genera métricas de
                                                    ocupación por inmueble,
                                                    ingresos liquidados y
                                                    listado de clientes
                                                    deudores.
  -------------------------------------------------------------------------

## Interfaz web --- Vistas Thymeleaf

El sistema incluye una interfaz web interactiva basada en Bootstrap 5.
Con la aplicación en ejecución local, se puede acceder a las siguientes
rutas:

  ------------------------------------------------------------------------------------
  Sección                             URL local
  ----------------------------------- ------------------------------------------------
  Clientes                            <http://localhost:8080/vista/clientes>

  Propiedades                         <http://localhost:8080/vista/propiedades>

  Disponibilidad                      <http://localhost:8080/vista/disponibilidades>

  Reservas                            <http://localhost:8080/vista/reservas>

  Pagos                               <http://localhost:8080/vista/pagos>

  Reportes                            <http://localhost:8080/vista/reportes>
  ------------------------------------------------------------------------------------

La ruta raíz de la aplicación también puede dirigir a la vista de
clientes: <http://localhost:8080>.

## Instalación y ejecución local

### Requisitos previos

-   **JDK 21** instalado.
-   **MySQL 8.x** instalado y en ejecución.
-   Maven instalado (opcional si se utiliza el Maven Wrapper incluido en
    el proyecto).

### 1. Clonar el repositorio

``` bash
git clone https://github.com/Angel-DeGranda/Apartamentos.git
cd Apartamentos
```

### 2. Crear la base de datos

Conéctate a MySQL y ejecuta:

``` sql
CREATE DATABASE IF NOT EXISTS apartamentos_blue
    CHARACTER SET utf8mb4;
```

La creación o actualización de tablas dependerá de la configuración de
Hibernate definida en `application.properties`.

### 3. Configurar las variables de entorno

Para evitar subir credenciales a GitHub, configura los datos de conexión
mediante variables de entorno. No escribas contraseñas reales en el
README ni las confirmes en Git.

En `src/main/resources/application.properties`, utiliza referencias a
variables de entorno, por ejemplo:

``` properties
spring.application.name=apartamentos

spring.datasource.url=${SPRING_DATASOURCE_URL}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}

server.port=${SERVER_PORT:8080}
```

Si el archivo ya contiene otras propiedades necesarias para el
funcionamiento del proyecto, consérvalas. No reemplaces todo el archivo
sin revisar la configuración actual.

#### macOS, Linux o Git Bash (Windows)

``` bash
export SPRING_DATASOURCE_URL="jdbc:mysql://localhost:3306/apartamentos_blue"
export SPRING_DATASOURCE_USERNAME="tu_usuario_mysql"
export SPRING_DATASOURCE_PASSWORD="tu_contraseña_mysql"
export SERVER_PORT="8080"
```

#### Windows PowerShell

``` powershell
$env:SPRING_DATASOURCE_URL = "jdbc:mysql://localhost:3306/apartamentos_blue"
$env:SPRING_DATASOURCE_USERNAME = "tu_usuario_mysql"
$env:SPRING_DATASOURCE_PASSWORD = "tu_contraseña_mysql"
$env:SERVER_PORT = "8080"
```

#### Windows CMD

``` bat
set SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/apartamentos_blue
set SPRING_DATASOURCE_USERNAME=tu_usuario_mysql
set SPRING_DATASOURCE_PASSWORD=tu_contraseña_mysql
set SERVER_PORT=8080
```

Los ejemplos utilizan marcadores de posición: sustitúyelos por las
credenciales configuradas en tu entorno local. Estas variables se
aplican a la sesión de terminal actual.

### Variables de entorno

  ------------------------------------------------------------------------------
  Variable                       Descripción             Obligatoria
  ------------------------------ ----------------------- -----------------------
  `SPRING_DATASOURCE_URL`        URL de conexión a MySQL Sí

  `SPRING_DATASOURCE_USERNAME`   Usuario de MySQL        Sí

  `SPRING_DATASOURCE_PASSWORD`   Contraseña del usuario  Sí
                                 de MySQL                

  `SERVER_PORT`                  Puerto HTTP de la       No; por defecto, `8080`
                                 aplicación              
  ------------------------------------------------------------------------------

### 4. Ejecutar la aplicación

Con las variables de entorno definidas en la terminal, inicia la
aplicación usando el Maven Wrapper.

**Windows PowerShell:**

``` powershell
.\mvnw.cmd spring-boot:run
```

**Windows CMD:**

``` bat
mvnw.cmd spring-boot:run
```

**macOS, Linux o Git Bash:**

``` bash
./mvnw spring-boot:run
```

Si no se incluye el Maven Wrapper en el repositorio y tienes Maven
instalado, puedes ejecutar:

``` bash
mvn spring-boot:run
```

Una vez iniciada correctamente, la aplicación estará disponible en
<http://localhost:8080>, o en el puerto que hayas definido mediante
`SERVER_PORT`.

## Manejo de errores HTTP

Los controladores REST gestionan los errores mediante
`@ExceptionHandler`. Los códigos esperados según la implementación
descrita son:

  ------------------------------------------------------------------------
  Código HTTP                         Condición / causa
  ----------------------------------- ------------------------------------
  `200 OK`                            Consulta o actualización realizada
                                      exitosamente.

  `201 Created`                       Registro creado correctamente en la
                                      base de datos.

  `204 No Content`                    Eliminación realizada con éxito, sin
                                      contenido en la respuesta.

  `400 Bad Request`                   Regla de negocio incumplida o datos
                                      de entrada inválidos, por ejemplo,
                                      una `IllegalArgumentException`.

  `404 Not Found`                     Recurso solicitado no encontrado por
                                      ID, por ejemplo, una
                                      `NoSuchElementException`.

  `409 Conflict`                      Conflicto de integridad referencial
                                      o clave foránea, por ejemplo, una
                                      `DataIntegrityViolationException`.
  ------------------------------------------------------------------------

Los mensajes y códigos exactos que devuelve la aplicación dependen del
manejo de excepciones implementado en los controladores.

## Recomendaciones antes de publicar en GitHub

-   No incluyas contraseñas reales en `application.properties`, en el
    README ni en otros archivos versionados.
-   Comprueba que `.gitignore` excluya archivos locales con secretos,
    como `.env` o configuraciones privadas.
-   Ten en cuenta que `.gitignore` no deja de rastrear archivos que ya
    fueron agregados a Git.
-   Antes de confirmar cambios, revisa `git status` y
    `git diff --cached`.
-   Si una contraseña real ya fue publicada, cámbiala o revócala; borrar
    el valor de un commit posterior no lo elimina necesariamente del
    historial.
-   Verifica que las rutas, los nombres de los campos y los códigos HTTP
    documentados coincidan con el código actual de la API.
