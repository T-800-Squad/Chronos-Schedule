# Gestión de horarios - Backend
**Parte del Módulo de administración: gestión de usuarios**  

## Descripción
Este "submódulo" implementa las funcionalidades de gestión de horarios de disponibilidad para usuarios administrativos del sistema, y es una extensión funcional del módulo de gestión de usuarios del sistema ECI Bienestar Total.
Aunque su código se encuentra separado por razones prácticas, permite a los administradores del sistema configurar la disponibilidad horaria para la atención, el uso de espacios y servicios institucionales.

## Funcionalidades principales
- Crear, editar, consultar y eliminar horarios de disponibilidad por servicio.
- Obtener la configuración horaria de un servicio por días.
- Gestionar parámetros como intervalos, pausas, entre otros.
- Asociar configuraciones a tipos de servicio o responsables.

## Tecnologías utilizadas
- **Spring Boot**: Framework principal para el desarrollo del backend en Java.
- **Maven**: Herramienta de gestión y construcción de proyectos.
- **MongoDB Atlas**: Base de datos no relacional utilizada para el almacenamiento de las configuraciones de los horarios.
- **JWT (JSON Web Tokens)**: Implementación de autenticación y autorización segura basada en tokens.

## Endpoints Principales

### Schedule Controller  
Operaciones CRUD sobre los horarios disponibles por servicio.

| Método | Ruta              | Descripción                                |
|--------|-------------------|--------------------------------------------|
| GET    | `/schedule`       | Obtener un horario específico              |
| POST   | `/schedule`       | Crear nuevos horarios                      |
| PUT    | `/schedule`       | Editar un horario existente                |
| DELETE | `/schedule`       | Eliminar todos los horarios de un servicio |
| GET    | `/schedule/service` | Obtener todos los servicios con horarios configurados |

### Configuration Controller  
Gestión de configuraciones globales de parámetros para horarios.

| Método | Ruta                   | Descripción                            |
|--------|------------------------|----------------------------------------|
| GET    | `/configuration`       | Obtener configuración actual           |
| POST   | `/configuration`       | Crear nueva configuración              |
| DELETE | `/configuration`       | Eliminar configuración existente       |
| GET    | `/configuration/name`  | Consultar configuración por nombre     |
| GET    | `/configuration/id`    | Consultar configuración por ID         |

## Pruebas Unitarias
Se implementaron pruebas unitarias para las capas funcionales del submódulo.
- `config`: verificación de autenticación mediante tokens JWT.
- `controller`: pruebas de creación, edición y eliminación de horarios, y pruebas de manejo de configuraciones horarias.
- `service`: lógica de negocio.

## Análisis de cobertura
![](https://github.com/T-800-Squad/Chronos-Schedule/blob/main/images/jacoco_schedule.png)
## Diagrama de clases
![](https://github.com/T-800-Squad/Chronos-Schedule/blob/main/images/diag_clases.png)
## Diagrama de arquitectura
![](https://github.com/T-800-Squad/Chronos-Schedule/blob/main/images/diag_arq.png)

## Relación con el Módulo de gestión de usuarios
Este submódulo no funciona de forma aislada, sino que:
- Se integra directamente con el módulo de gestión de usuarios.
- Es utilizado por UserController del módulo principal para permitir que perfiles como administradores, médicos, entrenadores configuren su disponibilidad horaria.
- Mantiene una arquitectura desacoplada para facilitar su escalabilidad y despliegue independiente.

## Notas Finales
- La mayoría de los endpoints están protegidos con autenticación JWT.
- Es clave para la correcta planificación y disponibilidad de los recursos y servicios del sistema.
