# 🧠 Foro Hub Challenge Back End

🚀 Proyecto backend del Challenge de Alura Latam. Esta API REST permite la gestión de un foro educativo donde usuarios pueden crear, visualizar, actualizar y eliminar tópicos, además de interactuar mediante respuestas. El sistema implementa autenticación mediante JWT, estructura modular escalable y pruebas unitarias. Actualmente, el rol implementado es `ROLE_USER`.

## 🧰 Tecnologías utilizadas

- Java 21
- Spring Boot
- Spring Security + JWT
- JPA + Hibernate
- PostgreSQL
- Maven
- JUnit 5 + Mockito
- Swagger

## 📌 Funcionalidades principales

- Crear un nuevo tópico `POST /topicos`
- Listar todos los tópicos con paginación `GET /topicos`
- Ver un tópico específico `GET /topicos/{id}`
- Editar un tópico `PUT /topicos/{id}`
- Eliminar un tópico `DELETE /topicos/{id}`
- CRUD completo para usuarios (`/usuarios`) y respuestas (`/respuestas`)
- Autenticación JWT
- Rol único implementado: `ROLE_USER`
- Exploración de endpoints vía Swagger

## 🧪 Validaciones y reglas de negocio

- Verificación de campos obligatorios
- Asociación de tópicos y respuestas al autor autenticado
- Control de acceso mediante anotaciones según rol

## 🔐 Seguridad

- Autenticación con JWT
- Rol actual: `ROLE_USER`
- Protección de rutas con filtros y anotaciones

## 📃 Documentación técnica

- Integración con Swagger para exploración interactiva
- Descripciones de endpoints, parámetros y respuestas

## 🔄 Paginación y ordenamiento

- Paginación integrada en `GET /topicos`
- Orden configurable por parámetros de URL

## 🧪 Testing unitario

- Pruebas con JUnit 5 y Mockito
- Validación de servicios, reglas de negocio y controladores

## 📂 Estructura del proyecto

```plaintext
src/
├── controller/
├── domain/
├── infra/
```

## 📸 Capturas de pantalla

*(Próximamente)*

## 🎥 Demo en video

*(Próximamente en YouTube)*

---

## 👨‍💻 Autor

Cristian Esteban Maida  
Backend Developer
📍 Buenos Aires, Argentina  
🔗 [LinkedIn](https://www.linkedin.com/in/cristian-esteban-maida)
