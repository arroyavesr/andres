# Guía para Compartir el Proyecto vía Git

Para compartir este proyecto y asegurar que cualquier otra persona pueda abrirlo en **Eclipse** o **IntelliJ** sin conflictos de librerías o rutas, hemos estandarizado el proyecto usando **Maven**.

## 1. El archivo `pom.xml` (La clave de la compatibilidad)
He creado un archivo `pom.xml` en la raíz del proyecto. Este archivo contiene la "receta" del proyecto:
*   Indica que usa Java 21.
*   Lista las librerías necesarias (`mysql-connector` y `jcalendar`).
*   Cuando otra persona abre este archivo, su IDE descarga automáticamente las librerías correctas desde internet, eliminando la necesidad de configurar manualmente la carpeta `lib/` o el "Build Path".

## 2. El archivo `.gitignore`
He añadido un archivo `.gitignore` para evitar subir archivos basura al repositorio de Git. Esto es crucial porque:
*   **No sube** archivos compilados (`.class` o la carpeta `bin/`).
*   **No sube** configuraciones personales de tu IDE (`.idea/`, `.project`, etc.).
*   Esto obliga al IDE de la otra persona a generar su propia configuración limpia basada en el `pom.xml`.

## 3. Pasos para compartir
1.  **Inicializar Git**: `git init` en la carpeta raíz.
2.  **Añadir archivos**: `git add .` (el `.gitignore` se encargará de filtrar lo innecesario).
3.  **Primer Commit**: `git commit -m "Proyecto estandarizado con Maven"`.
4.  **Subir a GitHub/GitLab**: Crea un repo remoto y sube tu código.

## 4. Instrucciones para el receptor
Dile a la persona que reciba el proyecto que:
*   **NO** intente importar el proyecto como un "Java Project" normal.
*   **SÍ** lo importe como un **"Maven Project"** (seleccionando el archivo `pom.xml`).

Esto configurará automáticamente el proyecto con el nombre correcto, los paquetes y todas las librerías sin que tengan que hacer nada manualmente.
