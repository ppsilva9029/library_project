# library_project

## **Library Management System**

### Es necesario ejecutar el query que viene en setup_query desde el Workbench de MySQL.

Este archivo define las tablas necesarias y al usuario y contraseña que se conecta con la base de datos.

### Gestión de libros
- Añadir nuevos libros.
- Eliminar libros existentes.
- Actualizar información de libros. 
- Buscar libros por diferentes criterios (título, autor, ISBN).

### Gestión de miembros
- Registrar nuevos miembros.
- Eliminar miembros existentes.
- Actualizar información de miembros.
- Buscar miembros por diferentes criterios (nombre, ID de miembro).

### Transacciones de préstamo
- Registrar un préstamo de libro.
- Registrar la devolución de un libro. 
- Ver historial de préstamos de un miembro.
- Ver disponibilidad de un libro.
 
### Requerimientos no funcionales  
- Interfaz de Usuario: Consola simple para interacciones del usuario.
- Persistencia: Utilizar una base de datos MySQL para almacenar datos de libros, miembros y transacciones.
- Manejo de Excepciones: Manejar adecuadamente todas las posibles excepciones que puedan surgir durante las operaciones del sistema.
- Documentación: Código bien documentado, instrucciones claras sobre cómo configurar y ejecutar el sistema.

# Database Schema

La base de datos está organizada en 3 tablas principales
  - tbl_libros
  - tbl_miembros
  - tbl_transacciones (Préstamos)

Cada tabla gestiona las operaciones referentes a Libros, Miembros y Préstamos, respectivamente.
