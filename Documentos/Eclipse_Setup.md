# Guía para abrir el proyecto en Eclipse

Este proyecto ha sido configurado para ser importado directamente en Eclipse IDE.

## Requisitos Previos
1. **Eclipse IDE**: Se recomienda una versión reciente (2023 o superior).
2. **Java JDK 21**: El proyecto está configurado para usar Java 21.
3. **Librerías externas**: 
   - El conector de MySQL ya ha sido incluido en la carpeta `lib/`.
   - **IMPORTANTE**: Debes colocar el archivo `jcalendar-1.4.jar` en la carpeta `lib/` si aún no está allí para que el componente `JDateChooser` funcione correctamente.

## Pasos para abrir el proyecto

1. **Abrir Eclipse IDE**.
2. Selecciona un **Workspace** (Espacio de trabajo).
3. Ve a **File** > **Import...**
4. En la ventana de importación, selecciona **General** > **Existing Projects into Workspace** y haz clic en **Next**.
5. En **Select root directory**, haz clic en **Browse...** y selecciona la carpeta del proyecto:
   `C:\tools\andres`
6. Asegúrate de que el proyecto "andres" esté marcado en la lista.
7. Haz clic en **Finish**.

## Estructura del Proyecto en Eclipse
- **src/**: Contiene el código fuente Java.
- **lib/**: Contiene las librerías `.jar` necesarias.
- **Documentos/**: Contiene la documentación del proyecto y scripts SQL.
- **bin/**: Carpeta donde Eclipse colocará los archivos compilados (generada automáticamente).

## Configuración de Base de Datos
Asegúrate de tener el contenedor de Docker iniciado como se describe en [Docker_Setup.md](./Docker_Setup.md) antes de ejecutar la aplicación.

Para ejecutar el programa principal:
1. Haz clic derecho sobre `Menuregistro.java` en el **Package Explorer**.
2. Selecciona **Run As** > **Java Application**.
