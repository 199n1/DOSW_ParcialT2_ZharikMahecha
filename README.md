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
### 

### Diseño de Interfaces — Figma

---

## ⚙️ Configuración de base de datos

Configura tus credenciales en `src/main/resources/application.yaml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/dosw_parcial
    username: postgres
    password: your_password
  data:
    mongodb:
      uri: mongodb://localhost:27017/dosw_parcial
```

---

## Cómo ejecutar

```bash
# Clonar el repositorio
git clone https://github.com/TU_USUARIO/DOSW_ParcialT2_ZharikMahecha.git

# Entrar al proyecto
cd DOSW_ParcialT2_ZharikMahecha

# Compilar
mvn clean install

# Ejecutar
mvn spring-boot:run
```

Swagger UI disponible en: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)