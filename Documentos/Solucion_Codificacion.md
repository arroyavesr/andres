# Solución de Problemas de Codificación de Caracteres

Si observas que los caracteres especiales (como acentos en "Coclé") se muestran incorrectamente (por ejemplo, "Cocl??"), sigue estos pasos para solucionarlo.

## Análisis del Problema
El problema ocurre porque la conexión entre la aplicación Java y la base de datos MySQL no está utilizando el juego de caracteres **UTF-8**. Por defecto, si no se especifica, la conexión puede intentar usar una codificación que no soporta acentos o símbolos especiales.

## Pasos para la Solución

### 1. Actualizar la URL de Conexión JDBC
Es necesario forzar el uso de Unicode y la codificación UTF-8 en el archivo fuente de Java. He actualizado los archivos `RegistroPersona.java` y `Searchupdatedelete.java` cambiando la URL de conexión:

**Antes:**
`jdbc:mysql://127.0.0.1:3308/personal?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true`

**Después:**
`jdbc:mysql://127.0.0.1:3308/personal?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=UTF-8`

### 2. Verificar la Base de Datos
Asegúrate de que tus tablas estén creadas con el charset `utf8`. El script [personal.sql](./personal.sql) ya incluye esta configuración:
```sql
ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
```

### 3. Reiniciar la Aplicación
Para que los cambios surtan efecto:
1.  Cierra la aplicación si está abierta.
2.  Compila el proyecto nuevamente para asegurar que los cambios en el código se apliquen.
3.  Vuelve a ejecutar la aplicación.

### 4. Corrección de Datos Existentes
Si ya hay registros en la base de datos que se guardaron "corruptos" (con los símbolos `??`), estos deberán ser actualizados manualmente o eliminados y vueltos a ingresar, ya que la corrección de la conexión evita que ocurra en el futuro pero no repara lo ya guardado de forma incorrecta.
