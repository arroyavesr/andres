# Ajustes de Validación de Datos - RegistroPersona.java

Se han realizado los siguientes ajustes en el programa para asegurar la integridad de los datos ingresados:

## 1. Correcciones Técnicas
- Se corrigió el import de `JDateChooser` (tenía una errata: `JDateChoose`).
- Se agregaron los imports faltantes: `java.awt.Color`, `java.text.SimpleDateFormat` y `java.util.regex.Pattern`.

## 2. Implementación de Validación
Se creó un método `validarCampos()` que se ejecuta antes de guardar cualquier registro. Las validaciones incluyen:

### Campos Obligatorios (No pueden estar vacíos)
- **Tomo**
- **Asiento**
- **Nombre 1**
- **Apellido 1**
- **Apellido c** (Apellido de Casada)
- **Comunidad**
- **Calle**
- **Casa**

### Controles de Selección
- **Fecha de Nacimiento**: Debe tener una fecha seleccionada.
- **Provincia**: Debe tener una opción seleccionada.
- **Distrito**: Debe tener una opción seleccionada.
- **Corregimiento**: Debe tener una opción seleccionada.

### Validaciones de Formato
- **Teléfono**: Se valida que tenga el formato completo (0000-0000).
- **Correo**: Se implementó una validación mediante Expresión Regular (Regex) para asegurar un formato de email válido (ejemplo@dominio.com).

## 3. Excepciones de Validación
Siguiendo las instrucciones, se omitió la validación de los siguientes campos:
- **Nombre 2**
- **Apellido 2**
- **UAC** (Usa Apellido de Casada)

## 4. Interfaz de Usuario
- Todas las alertas y mensajes de error se presentan mediante `JOptionPane`.
- Cuando ocurre un error de validación, el foco se sitúa automáticamente en el campo que causó el problema y se selecciona el texto para facilitar la corrección.
