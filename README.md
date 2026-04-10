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


##  Herramientas de modelado y diseño

### Modelado UML — Draw.io
>  *(Agregar una captura de pantalla de  cuenta de Draw.io)*

### Diseño de Interfaces — Figma
>  *(Agregar captura de pantalla de cuenta de Figma)*

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