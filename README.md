## Cómo probar la autenticación JWT con Postman

### 1. Registrar un usuario

- Método: **POST**
- URL: `http://localhost:8080/api/auth/register`
- Body → **raw** → **JSON**

```json
{
  "username": "jdoe",
  "email": "jdoe@example.com",
  "password": "StrongPass123",
  "firstName": "John",
  "lastName": "Doe",
  "university": "My University",
  "country": "Colombia",
  "studentId": "20230001"
}
```

Respuesta esperada: `200 OK` con los datos del usuario creado (sin contraseña).

---

### 2. Iniciar sesión y obtener el token JWT

- Método: **POST**
- URL: `http://localhost:8080/api/auth/login`
- Body → **raw** → **JSON**

```json
{
  "usernameOrEmail": "jdoe",
  "password": "StrongPass123"
}
```

Respuesta esperada:

```json
{
  "accessToken": "<JWT_AQUI>",
  "tokenType": "Bearer"
}
```

Copia el valor de `accessToken`.

---

### 3. Consultar el perfil del usuario autenticado (`/users/me`)

- Método: **GET**
- URL: `http://localhost:8080/api/auth/users/me`
- Headers:

`Authorization: Bearer <JWT_AQUI>`

Respuesta esperada: `200 OK` con el perfil del usuario (incluye `firstName`, `lastName`, `university`, `country`, `studentId`, etc.).

Si llamas este endpoint **sin** el header `Authorization` o con un token inválido, debe responder `401 Unauthorized`.

---

### 4. Actualizar el perfil del usuario autenticado

- Método: **PUT**
- URL: `http://localhost:8080/api/auth/users/me`
- Headers:

`Authorization: Bearer <JWT_AQUI>`

- Body → **raw** → **JSON** (solo los campos que quieras cambiar):

```json
{
  "firstName": "Johnny",
  "university": "New University",
  "country": "Peru"
}
```

Respuesta esperada: `200 OK` con el perfil actualizado.

---

### 5. Resumen de endpoints principales

- `POST /api/auth/register` → Registro de usuario (público).
- `POST /api/auth/login` → Login, devuelve `accessToken` JWT (público).
- `GET /api/auth/users/me` → Obtiene el perfil del usuario autenticado (requiere JWT).
- `PUT /api/auth/users/me` → Actualiza el perfil del usuario autenticado (requiere JWT).

---

## Documentación de cambios

El detalle técnico e histórico de los cambios del proyecto se mantiene en [CHANGELOG.md](CHANGELOG.md).

En ese archivo puedes consultar:

- Cambios añadidos, modificados y eliminados por fecha.
- Ajustes de configuración (Spring, JWT y datasource).
- Cambios de contenedorización (Dockerfile y Compose).
- Notas operativas para ejecutar el proyecto con variables de entorno.

---

## Cómo funciona la dockerización

La dockerización del proyecto se basa en dos piezas:

- `Dockerfile`: define la imagen de la aplicación Spring Boot.
- `compose.yaml`: orquesta la app (`usuarioservice`) y PostgreSQL (`db`) en una red compartida.

### 1) Construcción de la imagen de la aplicación

El `Dockerfile`:

- Usa `eclipse-temurin:23-jdk-alpine` como imagen base.
- Copia el JAR generado en `target/goslint-judge-0.0.1-SNAPSHOT.jar`.
- Arranca la app con `java -jar goslint-judge-0.0.1-SNAPSHOT.jar`.

Por eso, antes de construir imagen debes generar el JAR:

- `./mvnw clean package`

### 2) Orquestación con Docker Compose

En `compose.yaml` se levantan dos servicios:

- `usuarioservice`: contenedor de la API (puertos `8080` y `8081`).
- `db`: contenedor PostgreSQL 17 (puerto `5432`).

Ambos servicios se conectan a la red `mynetwork` para comunicarse por nombre de servicio (`db`).

### 3) Persistencia de datos

La base de datos usa un volumen bind local:

- `./data:/var/lib/postgresql/data`

Eso hace que los datos de PostgreSQL persistan entre reinicios de contenedores.

### 4) Variables de entorno necesarias

Compose espera estas variables:

- `DOCKER_USERNAME`
- `DOCKER_IMAGE_VERSION`
- `DB_NAME`
- `DB_USER`
- `DB_PASSWORD`
- `DB_HOST`

Nota: `compose.yaml` usa `image:` para `usuarioservice`, por lo que la imagen debe existir en un registry (o estar etiquetada localmente con ese mismo nombre/tag).

### 5) Flujo típico de uso

- Empaquetar app: `./mvnw clean package`
- Construir imagen: `docker build -t <usuario>/userservice:<version> .`
- Levantar servicios: `docker compose up -d`
- Ver logs: `docker compose logs -f usuarioservice`
- Detener servicios: `docker compose down`

