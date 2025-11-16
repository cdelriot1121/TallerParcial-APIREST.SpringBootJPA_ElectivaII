# Taller Parcial - API REST Spring Boot JPA

## Descripción
API REST desarrollada con Spring Boot, Spring JPA y Spring Security para una plataforma de e-commerce online. El proyecto incluye autenticación JWT, gestión de usuarios, productos, comentarios y carritos de compras.

## Tecnologías Utilizadas
- Spring Boot 3.5.7
- Spring Data JPA
- Spring Security
- JWT (JSON Web Token)
- MySQL
- Lombok
- Maven

## Configuración de Base de Datos

### Requisitos Previos
1. MySQL Server instalado
2. Base de datos `taller_parcial_spring_boot_jpa` (se crea automáticamente)
3. Usuario root con contraseña `rootcar` (modificar en `application.properties` si es diferente)

### Configuración
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/taller_parcial_spring_boot_jpa?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=rootcar
```

## Instalación y Ejecución

1. Clonar el repositorio
```bash
git clone [URL_DEL_REPOSITORIO]
cd TallerParcialSpringBootJPA
```

2. Compilar el proyecto
```bash
mvn clean install
```

3. Ejecutar la aplicación
```bash
mvn spring-boot:run
```

La aplicación estará disponible en `http://localhost:8080`

## Autenticación

### Registro de Usuario
**POST** `/api/auth/signup`

Request Body:
```json
{
    "nombre": "Nombre Completo",
    "correoElectronico": "usuario@email.com",
    "contraseña": "password123",
    "direccion": "Dirección de envío",
    "metodoDePago": "Tarjeta de crédito"
}
```

### Inicio de Sesión
**POST** `/api/auth/signin`

Request Body:
```json
{
    "correoElectronico": "usuario@email.com",
    "contraseña": "password123"
}
```

Response:
```json
{
    "token": "eyJhbGciOiJIUzUxMiJ9...",
    "type": "Bearer",
    "id": 1,
    "email": "usuario@email.com",
    "nombre": "Nombre Completo"
}
```

## Endpoints de la API

### 🔓 Endpoints Públicos (No requieren autenticación)

#### 1. Listar Productos por Stock
**GET** `/api/productos/listar?stockMaximo=15`
- Devuelve productos con stock menor al número especificado

#### 2. Listar Comentarios por Fecha
**GET** `/api/comentarios/listar?fechaDesde=2024-01-01`
- Devuelve comentarios desde la fecha especificada

### 🔒 Endpoints Protegidos (Requieren JWT)

**Header requerido:**
```
Authorization: Bearer [JWT_TOKEN]
```

#### 3. Crear Carrito
**POST** `/api/carrito/crear`
- Crea un nuevo carrito para el usuario logueado

#### 4. Agregar Producto al Carrito
**POST** `/api/carrito/{idCarrito}/agregar-producto`

Request Body:
```json
{
    "idProducto": 1,
    "cantidad": 2
}
```
- Descuenta automáticamente del stock
- Solo el dueño del carrito puede agregar productos

#### 5. Listar Productos del Carrito
**GET** `/api/carrito/{idCarrito}/productos`
- Lista los productos de un carrito específico
- Solo el dueño del carrito puede ver sus productos

#### 6. Listar Mis Carritos
**GET** `/api/carrito/mis-carritos`
- Lista todos los carritos del usuario logueado

## Usuarios de Prueba

El sistema incluye usuarios predefinidos con contraseñas encriptadas:

| Email | Contraseña | Nombre |
|-------|------------|--------|
| juan.perez@email.com | password123 | Juan Pérez |
| ana.gomez@email.com | password123 | Ana Gómez |
| carlos.ruiz@email.com | password123 | Carlos Ruiz |
| sofia.martinez@email.com | password123 | Sofía Martínez |
| diego.fernandez@email.com | password123 | Diego Fernández |

## Pruebas con Postman

### 1. Autenticación
```http
POST http://localhost:8080/api/auth/signin
Content-Type: application/json

{
    "correoElectronico": "juan.perez@email.com",
    "contraseña": "password123"
}
```

### 2. Listar Productos (Público)
```http
GET http://localhost:8080/api/productos/listar?stockMaximo=20
```

### 3. Crear Carrito (Protegido)
```http
POST http://localhost:8080/api/carrito/crear
Authorization: Bearer [TOKEN_JWT]
```

### 4. Agregar Producto al Carrito
```http
POST http://localhost:8080/api/carrito/1/agregar-producto
Authorization: Bearer [TOKEN_JWT]
Content-Type: application/json

{
    "idProducto": 1,
    "cantidad": 2
}
```

### 5. Ver Productos del Carrito
```http
GET http://localhost:8080/api/carrito/1/productos
Authorization: Bearer [TOKEN_JWT]
```

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/example/TallerParcialSpringBootJPA/
│   │   ├── controller/          # Controladores REST
│   │   ├── dto/                 # Data Transfer Objects
│   │   ├── entities/            # Entidades JPA
│   │   ├── repository/          # Repositorios JPA
│   │   ├── security/            # Configuración de seguridad JWT
│   │   └── service/             # Lógica de negocio
│   └── resources/
│       ├── sql/                 # Scripts SQL
│       └── application.properties
```

## Características Implementadas

✅ **Autenticación y Autorización**
- JWT para autenticación
- Contraseñas encriptadas con BCrypt
- Endpoints públicos y protegidos

✅ **Gestión de Usuarios**
- Registro e inicio de sesión
- Perfil de usuario

✅ **Gestión de Productos**
- Listado público por stock
- Control de inventario automático

✅ **Gestión de Comentarios**
- Listado público por fecha
- Comentarios por producto

✅ **Gestión de Carritos**
- Creación de carritos por usuario
- Adición de productos con descuento de stock
- Listado de productos por carrito
- Validación de permisos (solo el dueño puede ver/modificar)

✅ **Seguridad**
- Spring Security con JWT
- Validación de permisos por endpoint
- Encriptación de contraseñas

## Endpoints Adicionales

### Gestión de Productos (Protegidos)
- **GET** `/api/productos/todos` - Todos los productos
- **GET** `/api/productos/{id}` - Producto por ID
- **POST** `/api/productos` - Crear producto

### Gestión de Comentarios (Protegidos)
- **GET** `/api/comentarios/todos` - Todos los comentarios
- **GET** `/api/comentarios/producto/{idProducto}` - Comentarios por producto
- **POST** `/api/comentarios` - Crear comentario

### Gestión de Usuario (Protegidos)
- **GET** `/api/usuarios/perfil` - Ver perfil
- **PUT** `/api/usuarios/perfil` - Actualizar perfil

## Notas Importantes

1. **Stock Management**: Al agregar productos al carrito, el stock se descuenta automáticamente
2. **Security**: Los carritos solo pueden ser accedidos por sus propietarios
3. **JWT**: Los tokens tienen una validez de 24 horas
4. **Database**: Se inicializa automáticamente con datos de prueba

## Autores
- [Nombres de los integrantes del grupo]

## Repositorio
[URL del repositorio en GitHub]