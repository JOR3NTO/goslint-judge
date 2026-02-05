## Feature: Registro y consulta de usuarios

Esta feature permite:

- Registrar nuevos usuarios.
- Consultar la lista de usuarios registrados.

Se implementa con:

- Controlador: `AuthController` (`/api/auth`)
- Servicio: `AuthService`
- Modelos: `RegisterRequest`, `UserResponse`
- Entidad JPA: `User`

### 1. Requisitos previos

- Java 17+ (o la versión que use el proyecto).
- Maven.
- PostgreSQL con una base de datos creada, por ejemplo:

  - BD: `goslint_judge`
  - Usuario: `goslint_judge`
  - Password: `JorentO215a3sur34*`

Configurados en `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/goslint_judge
spring.datasource.username=goslint_judge
spring.datasource.password=JorentO215a3sur34*
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

### 2. Cómo levantar la aplicación

Desde la raíz del proyecto:

```bash
mvn spring-boot:run
```

La API quedará escuchando por defecto en:

```text
http://localhost:8080
```

---

### 3. Endpoints de la feature

#### 3.1. Registro de usuario

- **Método:** `POST`
- **URL:** `http://localhost:8080/api/auth/register`
- **Headers:**
  - `Content-Type: application/json`
- **Body (JSON):**

```json
{
  "username": "eduar",
  "email": "eduar@example.com",
  "password": "MiClaveSegura123"
}
```

- **Respuestas:**
  - `200 OK` → registro exitoso, devuelve un `UserResponse` (sin contraseña).
  - `400 Bad Request` → datos inválidos o username/email duplicados.

##### Probar en Postman

1. Crear una nueva petición.
2. Seleccionar método `POST`.
3. URL: `http://localhost:8080/api/auth/register`.
4. Pestaña **Body** → `raw` → `JSON`.
5. Pegar el JSON del ejemplo y enviar.

---

#### 3.2. Listar todos los usuarios

- **Método:** `GET`
- **URL:** `http://localhost:8080/api/auth/users`
- **Headers:** ninguno obligatorio.
- **Body:** vacío.

- **Respuesta de ejemplo (`200 OK`):**

```json
[
  {
    "id": 1,
    "username": "eduar",
    "email": "eduar@example.com"
  },
  {
    "id": 2,
    "username": "otro",
    "email": "otro@example.com"
  }
]
```

##### Probar en Postman

1. Crear una nueva petición.
2. Seleccionar método `GET`.
3. URL: `http://localhost:8080/api/auth/users`.
4. Dejar el **Body** vacío.
5. Enviar la petición.

---

### 4. Notas de desarrollo

- La seguridad se configura en `SecurityConfig` para permitir el acceso público a los endpoints de autenticación/registro.
- Las contraseñas se almacenan cifradas utilizando `BCryptPasswordEncoder` de Spring Security.
- Las tablas se crean/actualizan automáticamente gracias a `spring.jpa.hibernate.ddl-auto=update`.
