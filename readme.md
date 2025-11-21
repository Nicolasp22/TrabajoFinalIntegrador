# Trabajo Final Integrador - Programacion 2

Este es el Trabajo Final Integrador para la materia Programacion 2 de la UTN.
El proyecto implementa un sistema CRUD (Crear, Leer, Actualizar, Borrar) para gestionar Productos y sus Codigos de Barras, usando una arquitectura en capas (DAO/Service) y transacciones JDBC.

*Dominio Elegido:* 9. Producto -> CodigoBarras

---

## Integrantes

* *Matias:* Arquitectura, Capa Service y DAO.
* *Fernando:* Handler, Diseno de Base de Datos y Capa DAO.
* *Nicolas:* Desarrollo de Capa de Presentacion - Menu,.
* *Rodrigo:* Base de datos, Pruebas y Documentacion.

---

## Requisitos

Para poder correr este proyecto, necesitas tener instalado:

* Java JDK (version 21 es la recomendada).
* Un IDE para Java (como NetBeans 19 o mas nuevo).
* Un servidor de base de datos MySQL.
* *Recomendado:* Usar *XAMPP*, ya que es la forma mas facil de tener MySQL y Apache corriendo.
* El *Driver/Conector de MySQL* (el archivo .jar).

---

## Como Probar el Proyecto (Paso a Paso)

Estos son los pasos para levantar el proyecto desde cero en una computadora nueva.

### 1. Preparar la Base de Datos (MySQL)

1.  Asegurate de que tu servidor MySQL este corriendo (por ejemplo, inicia Apache y MySQL en el panel de control de XAMPP).
2.  Abre tu cliente de base de datos (como MySQL Workbench o phpMyAdmin).
3.  Crea una nueva base de datos (schema) con el nombre exacto: gestion_comercio2
4.  Abre el archivo 01_esquema.sql (que esta en el repositorio) y ejecuta todo el script. Esto creara todas las tablas.
5.  Abre el archivo 02_catalogos.sql (en el repositorio) y ejecutalo. Esto cargara las Marcas, Categorias y Tipos de Codigo de Barras.

### 2. Configurar el Proyecto en NetBeans

1.  Abre el proyecto (la carpeta src) en NetBeans.
2.  *Importante: Agrega el Driver de MySQL:*
    * Haz clic derecho sobre el nombre del proyecto (en el panel izquierdo).
    * Ve a *Properties* (Propiedades).
    * Ve a la seccion *Libraries* (Bibliotecas).
    * En la pestana *Classpath, haz clic en el boton *+* y selecciona **Add JAR/Folder*.
    * Busca el archivo mysql-connector-j-X.X.XX.jar que descargaste y agregalo.

### 3. Configurar tu Conexion Local
En NetBeans, andá al paquete Config y seleccioná el archivo DatabaseConnection.java
Dentro de la clase DatabaseConnection, rellená las variables USER y PASSWORD con tu usuario y contraseña reales de MySQL (si usás XAMPP, suele ser usuario "root" y contraseña vacía "").
Guardá los cambios y listo. Si usas XAMPP por defecto, deberia ser asi:

    java
    // ...
    private static final String URL = System.getProperty("db.url", "jdbc:mysql://localhost:3306/gestion_comercio2");
    private static final String USER = System.getProperty("db.user", "root");
    private static final String PASSWORD = System.getProperty("db.password", ""); // (vacio)
    // ...
    

### 4. Ejecutar el Programa

1.  Haz clic derecho en el archivo src/Main/Main.java.
2.  Selecciona *Run File*.
3.  ¡Listo! El menu deberia aparecer en la consola.

---

## Probar los Tests

Si queres verificar que todo funciona rapido sin usar el menu, podes correr los tests:

* *Test de Conexion:* src/Tests/TestConexion.java (Verifica solo la conexion).
* *Test del Flujo (E2E):* src/Tests/TestMenuHandlerSimple.java y TestProductoServiceImp.java (Prueba el flujo de crear y listar).
* *Test de inserción individual:* src/Tests/TestProductoServiceImp.java (prueba el Insert de un nuevo producto mediante llamado al service).
---

## Video de Demostracion
https://youtu.be/S7mpLVC7DIo
