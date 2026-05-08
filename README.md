# Proyecto Sistema de Registro Java Swing

Este es un proyecto de escritorio desarrollado en Java Swing con conexión a MySQL.

## Requisitos
*   **Java 21** o superior.
*   **Docker** (para la base de datos) o una instancia de MySQL local.
*   **Maven** (opcional, integrado en la mayoría de los IDEs).

## Estructura del Proyecto
*   `src/`: Contiene el código fuente `.java`.
*   `lib/`: Librerías locales (legacy).
*   `Documentos/`: Scripts SQL y documentación de configuración.
*   `pom.xml`: Configuración de Maven para gestión automática de dependencias.

## Cómo Abrir el Proyecto
Este proyecto está estandarizado con **Maven**, lo que garantiza compatibilidad universal.

### En IntelliJ IDEA
1.  `File > Open` y selecciona el archivo `pom.xml`.
2.  Elige `Open as Project`.
3.  IntelliJ descargará automáticamente las librerías (`mysql-connector` y `jcalendar`).

### En Eclipse
1.  `File > Import...`
2.  Selecciona `Maven > Existing Maven Projects`.
3.  Busca la carpeta del proyecto y selecciona el `pom.xml`.
4.  Haz clic en `Finish`.

## Configuración de la Base de Datos
1.  Asegúrate de tener Docker corriendo.
2.  Ejecuta los comandos en [Documentos/Docker_Setup.md](./Documentos/Docker_Setup.md) para levantar el contenedor.
3.  Importa el script [Documentos/personal.sql](./Documentos/personal.sql) si es necesario.

## Ejecución
La clase principal es `src/Menuregistro.java`.
