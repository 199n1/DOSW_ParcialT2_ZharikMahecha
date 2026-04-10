# DOSW_ParcialT2_ZharikMahecha



| Campo         | Detalle             |
|---------------|---------------------|
| **Nombre**    | Zharik Mahecha      |
| **Grupo**     | Grupo 1             |


---

##  Tecnologías del proyecto

- Java 17
- Spring Boot 3.2.4
- PostgreSQL + JPA
- MongoDB
- Spring Security + JWT
- Lombok + MapStruct
- Swagger (SpringDoc OpenAPI)
- JUnit 5 + Mockito
- JaCoCo
- SonarQube
- SLF4J

---


# Parte Teorica

### Punto 3 - Dieferencia entre autenticación, autorización e integridad

-Autentificacion: es la forma en que el sistema valida la identificacion del usuario que intenta acceder

-Autorizacion:  Es la definicion de que puede hacer el usuario dentro del sistemas, los permisos que se le otorgan

-Integridad: Es la garantizacion de que la informacion no sera modificada ni alterada durante el proceso de almacenamiento

---
### Punto4 - Diagrama de componentes general ECIEXPRESS 

![img.png](docs/images/ComponentesGeneral.png)

El usuario usa la plataforma mediante la interfaz, usando el qr, luego esta hace las peticiones a todo el sistema interno,
el cual va almacenando la informacion dentro de la BD, este mismo devuelve la interaccion al fronted y retornara la 
informacion que usuario haya pedido

### Diseño de Interfaces — Figma

---

## 
## 
```

Swagger UI disponible en: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)