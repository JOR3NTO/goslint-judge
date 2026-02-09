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

