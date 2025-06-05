# Prueba Dev Backend

Este microservicio está desarrollado con Java 21 y Spring Boot 3.5. Permite gestionar franquicias, sucursales y productos. Utiliza MongoDB Atlas como base de datos, y Terraform para aprovisionamiento de infraestructura.

---

## Autor

Nombre: Alex Cudriz  
Email: alex.d.cudriz@accenture.com  
GitHub: [https://github.com/alexdavcudriz](https://github.com/alexdavcudriz)

---

## Características

- CRUD de franquicias, sucursales y productos
- Consulta del producto con mayor stock por sucursal
- Documentación con Swagger UI
- Deploy en Docker
- Infraestructura como código con Terraform (MongoDB Atlas)

---

## Levantar el microservicio con Docker

### 1. Compilar el proyecto

./mvnw clean package -DskipTests

### 2. Construir imagen Docker

docker build -t prueba-dev-backend .

### 3. Ejecutar contenedor

docker run -p 8080:8080 prueba-dev-backend

### 3. Comprobar ejecución

Acceder a Swagger UI en: https://pruebadevbackend.onrender.com/webjars/swagger-ui/index.html  
O en local: http://localhost:8080/swagger-ui.html

---

## Aprovisionar MongoDB Atlas con Terraform

### 1. Requisitos

- Cuenta en MongoDB Atlas
- Crear API Key (Organización > Access Manager > API Keys)
- Instalar Terraform

### 2. Comprobar archivo terraform.tfvars

Comprobar y/o actualizar las key de conexión en el archivo terraform.tfvars:  
- public_key  
- private_key  
- org_id

### 3. Ejecutar Terraform

Ejecuta los siguientes comando en consola:

- cd terraform  
- terraform init  
- terraform plan  
- terraform apply

---

## Endpoints principales

| Método | Endpoint                                 | Descripción                                  |
|--------|------------------------------------------|----------------------------------------------|
| GET    | /api/franquicias                         | Listar franquicias                           |
| POST   | /api/franquicias                         | Crear franquicia                             |
| PATCH  | /api/franquicias/{id}                    | Actualizar nombre de franquicia              |
| POST   | /api/franquicias/{id}/sucursales         | Agregar sucursal a franquicia                |
| PATCH  | /api/sucursales/{id}                     | Actualizar nombre de sucursal                |
| POST   | /api/sucursales/{id}/productos           | Agregar producto a sucursal                  |
| PATCH  | /api/productos/{id}                      | Actualizar nombre de producto                |
| PATCH  | /api/productos/{id}/stock                | Actualizar stock de producto                 |
| GET    | /api/franquicias/{id}/mayor-stock        | Producto con mayor stock por sucursal        |

---

## Licencia

Este proyecto está bajo la Licencia MIT.
