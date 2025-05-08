# Medical System

Este proyecto es una aplicación backend desarrollada con **Spring Boot** que implementa un sistema de consulta médica.

---

## 🚀 Tecnologías utilizadas

- Java 17  
- Spring Boot 3.4.5  
- Spring Data JPA  
- PostgreSQL  
- Flyway  
- SpringDoc (OpenAPI)

---

## 📋 Requisitos

- Java 17  
- PostgreSQL (versión 12 o superior recomendada)  
- Maven (incluido en el wrapper)

---

## ⚙️ Configuración e instalación

1. **Clona el repositorio:**

```bash
git clone https://github.com/julian-pena/medical-system.git
cd medical-system
```


## Configura las variables de entorno para la base de datos:

Crea un archivo .env (o configúralas directamente en tu sistema) con el siguiente contenido:

```env
DB_URL=jdbc:postgresql://localhost:5432/medical_db
DB_USERNAME=tu_usuario
DB_PASSWORD=tu_contraseña
```
Asegúrate de que la base de datos **medical_db** exista y que el usuario tenga permisos suficientes.

Ejecuta la aplicación:

```bash
./mvnw spring-boot:run
```

### La aplicación se iniciará en: http://localhost:8080

## 📚 Documentación de la API
La documentación interactiva generada por SpringDoc está disponible en:

http://localhost:8080/swagger-ui.html

