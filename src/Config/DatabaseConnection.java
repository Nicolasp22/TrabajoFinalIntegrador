/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author notvo
 */
public final class DatabaseConnection {

    /**
     * URL de conexion JDBC. Configurable via -Ddb.url
     */
    private static final String URL = System.getProperty("db.url", "jdbc:mysql://localhost:3306/gestion_comercio2");

    /**
     * Usuario de la base de datos. Configurable via -Ddb.user
     */
    private static final String USER = System.getProperty("db.user", "root");

    /**
     * Password del usuario. Configurable via -Ddb.password
     */
    private static final String PASSWORD = System.getProperty("db.password", "");

    static {
        try {
            // Carga explicita del driver (requerido en algunas versiones de Java)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Valida configuracion tempranamente (fail-fast)
            validateConfiguration();
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError("Error: No se encontro el driver JDBC de MySQL: " + e.getMessage());
        } catch (IllegalStateException e) {
            throw new ExceptionInInitializerError("Error en la configuracion de la base de datos: " + e.getMessage());
        }
    }

    // constructor privado para evitar instanciacion ya que usamos todo como static
    private DatabaseConnection() {
        throw new UnsupportedOperationException("Esta es una clase utilitaria y no debe ser instanciada");
    }

    // nueva conexion 
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    
    // validar los parametros de configuracion sean validos 
    
        private static void validateConfiguration() {
        if (URL == null || URL.trim().isEmpty()) {
            throw new IllegalStateException("La URL de la base de datos no esta configurada");
        }
        if (USER == null || USER.trim().isEmpty()) {
            throw new IllegalStateException("El usuario de la base de datos no esta configurado");
        }
        // PASSWORD puede ser vacio (comun en MySQL local con usuario root sin password)
        // Solo validamos que no sea null
        if (PASSWORD == null) {
            throw new IllegalStateException("La contrasena de la base de datos no esta configurada");
        }
    }

}
