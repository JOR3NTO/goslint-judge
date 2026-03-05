# Changelog

## 2026-03-05

### Añadido

- Dependencia `spring-boot-starter-actuator` en `pom.xml`.
- Archivo `Dockerfile` para ejecutar el JAR de la aplicación.
- Archivo `compose.yaml` con servicios:
  - `usuarioservice` (Spring Boot)
  - `db` (PostgreSQL 17 Alpine)
- Archivo `.gitignore` para excluir artefactos de build, datos locales de PostgreSQL y archivos de IDE/SO.
- Configuración de Maven Wrapper en `.mvn/wrapper/maven-wrapper.properties`.
- Configuración principal en `src/main/resources/application.yml`.

### Cambiado

- Migración de configuración de `application.properties` a `application.yml`.
- Configuración de datasource basada en variables de entorno:
  - `DB_HOST` (URL JDBC completa)
  - `DB_USER`
  - `DB_PASSWORD`
- Inclusión de propiedades JWT en YAML:
  - `app.jwt.secret`
  - `app.jwt.expiration-ms`
- Script `mvnw` actualizado con permisos de ejecución.

### Eliminado

- Archivo `src/main/resources/application.properties`.

### Notas operativas

- Si no están definidas las variables de datasource, Spring puede fallar con:
  - `Failed to determine a suitable driver class`
- Variables mínimas esperadas para Compose:
  - `DOCKER_USERNAME`
  - `DOCKER_IMAGE_VERSION`
  - `DB_NAME`
  - `DB_USER`
  - `DB_PASSWORD`
  - `DB_HOST`
