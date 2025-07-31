# Foro Hub Challenge Back End

🚀 Proyecto backend del Challenge de Alura Latam. Esta API REST permite la gestión de un foro educativo donde usuarios pueden crear, visualizar, actualizar y eliminar tópicos, además de interactuar mediante respuestas. El sistema implementa autenticación mediante JWT y maneja roles para controlar el acceso a los recursos.

## 🧰 Tecnologías utilizadas

- Java 21
- Spring Boot
- Spring Security + JWT
- JPA + Hibernate
- PostgreSQL
- Maven

## 📌 Funcionalidades

- Crear un nuevo tópico `POST /topicos`
- Listar todos los tópicos `GET /topicos`
- Ver un tópico específico `GET /topicos/{id}`
- Editar un tópico `PUT /topicos/{id}`
- Eliminar un tópico `DELETE /topicos/{id}`
- Autenticación de usuarios con JWT
- Roles para usuarios, moderadores y administradores

## 🧪 Validaciones y reglas de negocio

- Verificación de campos obligatorios
- Asociación del tópico y respuesta al autor autenticado
- Control de acceso según roles

## 🔐 Seguridad

- Autenticación con JWT
- Roles: USER, MODERATOR, ADMIN
- Protección de rutas mediante filtros y anotaciones

## 🎯 Próximas mejoras

- Documentación con Swagger
- Paginación y ordenamiento
- Pruebas unitarias con JUnit y Mockito

## 📸 Capturas de pantalla

*(Próximamente)*

## 🎥 Demo en video

*(Próximamente en YouTube)*

---

## 📂 Estructura del proyecto

```plaintext
src/
├── controller/
├── service/
├── repository/
├── model/
├── dto/
└── config/
```

## 👨‍💻 Autor

Cristian Esteban Maida  
Backend Developer | UTN Avellaneda  
📍 Buenos Aires, Argentina  
🔗 [LinkedIn](https://www.linkedin.com/in/cristian-esteban-maida)
