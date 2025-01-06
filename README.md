# MELI - Shortlinker - 'mely.ly'

Necesitamos hacer un urlshortener tipo goo.gl o bitly para publicar promociones en twitter.
Armar la arquitectura de la solución que satisfaga los siguientes requisitos:
- Las urls tienen una vigencia indefinida.
- La plataforma debe soportar picos de tráfico alrededor de 1M RPM.
- Debemos contar con estadísticas de acceso nearly real time.
- Las URLs publicadas deben poder ser administradas:
  - Habilitarse o deshabilitarse
  - Modificar la url de destino (cualquiera de sus componentes)
- A su vez la resolución de urls debe realizarse lo más rápido y con el menor costo
  posible.
- La funcionalidad debe ser operativa en un 99,98% del tiempo (uptime)

A tener en cuenta:
- Planteo de distintos componentes explicando su responsabilidad y el por qué de su
inclusión.
- Explicación de la infraestructura, herramientas/tecnologías preexistentes y cuales
son los motivos de la elección de cada una (por ejemplo si se incluye una ddbb en
particular, comentar si se evaluaron otras alternativas y cuál fue el racional de la
decisión final).
- Es deseable incluir gráficos y explicación breve escrita para que la propuesta sea
correctamente entendida.
- Es deseable entregar una API Rest funcional corriendo en algún cloud público, con
una capacidad aproximada de 5000 rpm (verificable) a modo de demo (no hace falta
gastar dinero en la prueba, mockear lo que no pueda obtenerse gratis).
- El código debe ser compartido a través de un repositorio o bien en un zip.

## Project Setup (Solo para Linux)
1. Clona el repositorio
2. Abrir el proyecto en IntelliJ IDEA
3. Configurar el SDK del proyecto a Java 21 en tu IDE
4. Descargar las dependencias de maven
5. En la termina ejecutar el comando `docker compose up -d`
6. API disponible en `http://localhost:8081`
7. Para eliminar los contenedores ejecutar `docker compose down -v`

##### Credenciales
- Postgres:
  - POSTGRES_USER=link
  - POSTGRES_PASSWORD=secret-link
  - POSTGRES_DB=shortlinker
- Redis:
  - N/A
- Grafana:
  - Usuario: `admin`
  - Contrasenia: `admin`

### Para configurar obserbailidad con Prometheus & Grafana (Opcional)
1. En el archivo `prometheus/prometheus.yml` en la línea 9 y en `grafana/grafana.yml` en la línea 7 deberan poner la ip local de su docker. Ej: `172.17.0.1`
    - Esta ip la pueden obetner con el comando `ip addr show docker0`
    - El output es algo asi: `"inet 172.17.0.1/16 brd 172.17.255.255 scope global docker0"`
2. En el archivo `prometheus/prometheus.yml` en la línea 20 deberan poner la ip de su maquina local. Ej: `192.168.1.33`
    - Esta ip la pueden obetner con el comando `hostname -I`
    - El output es algo asi: `192.168.1.33 172.17.0.1 172.18.0.1`
3. Ejecutar `docker compose down -v` y luego `docker compose up -d`
4. Configurar Grafana:
    - Ingresar a `http://localhost:3000`
    - Usuario: `admin`
    - Contrasenia: `admin`
    - Skip "Update your password"
    - Dashboards -> New -> Import -> dashboard URL or ID "`19004`" -> Load -> Select Prometheus data source -> "`Prometheus`" -> Import

- HOST GRAFANA: `localhost:3000`  
- HOST PROMETHEUS: `localhost:9090/targets`

## Varios
- Se agrego Unit Test para `RedisUrlCacheRepository` & `UrlServiceImpl`
- Uso de inyección de dependencias con `@Autowired` y constructor
- Uso de validation con `@Valid` y `@NotNull`
- Uso de interfaces entra las diferentes capas de la aplicación
- Implementacion del algoritmo de base62 para la generación de shortlinks (generacion de slug)

## Deuda tecnica segun el alcance previsto
- Implementar test para el resto de las clases
- Implementar test de performance (5000 rpm)
- Subir API a un host público

# Desing & Implementation
![melily](https://github.com/user-attachments/assets/05297f40-3a74-4f15-8077-6a9e405e6ea8)
