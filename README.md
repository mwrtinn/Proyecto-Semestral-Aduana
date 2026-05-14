# Proyecto Semestral: Aduana
## Integrantes: Martin Faundez, Vicente Vega

### Estado del Sistema (Hito 1)
| Microservicio | Puerto | DB Name | Funcionalidad |
| :--- | :--- | :--- | :--- |
| Usuarios | 8080 | db_usuario | Registro de usuarios, gestión de roles y administración de perfiles |
| Vehiculos | 8081 | db_vehiculo | CRUD de vehículos, control de patentes y validación de números de chasis (VIN) |
| Mascotas | 8082 | db_mascota | Registro de mascotas, trazabilidad de microchips y control sanitario (vacunas) |
| Menores | 8083 | db_menor| Validación de permisos de viaje y registro de actas notariales |
| Declaraciones | 8084 | db_declaracion | Declaración jurada de mercancías y valoración aduanera (USD) |
| Aduanas | 8085 | db_aduana | Registro de cruces fronterizos, tracking de complejos y validación de ingresos/egresos con ms-usuario |
| Correos | 8086 | db_correo | Registro de encomiendas internacionales, tracking de paquetes, control de peso y gestión de estado aduanero (Retenido/Liberado). |
### Despliegue Técnico
- **Instancia:** AWS EC2 t3.large (Ubuntu 24.04)
- **Comando de inicio:** `docker compose up -d`
