# Configuración de Base de Datos MySQL con Docker

Este documento describe cómo se ha configurado el entorno de base de datos para el proyecto utilizando Docker.

## Detalles del Contenedor
- **Nombre**: `mysql-projecto1`
- **Puerto Host**: `3308`
- **Puerto Contenedor**: `3306`
- **Base de Datos**: `personal`
- **Usuario**: `d32026`
- **Contraseña**: `123`
- **URL JDBC**: `jdbc:mysql://127.0.0.1:3308/personal`

## Comandos Utilizados para la Creación

```powershell
# 1. Crear e iniciar el contenedor
docker run --name mysql-projecto1 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=personal -e MYSQL_USER=d32026 -e MYSQL_PASSWORD=123 -p 3308:3306 -d mysql:latest

# 2. Importar el script de base de datos
Get-Content Documentos/personal.sql | docker exec -i mysql-projecto1 mysql -u root -proot personal
```

## Comandos Útiles de Gestión

### Iniciar el contenedor
```powershell
docker start mysql-projecto1
```

### Detener el contenedor
```powershell
docker stop mysql-projecto1
```

### Ver logs
```powershell
docker logs -f mysql-projecto1
```

### Acceder a la consola de MySQL
```powershell
docker exec -it mysql-projecto1 mysql -u d32026 -p123 personal
```

## Verificación de Tablas
Las siguientes tablas fueron creadas exitosamente:
- `corregimiento`
- `datos`
- `distrito`
- `portapapeles`
- `provincia`
