/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tests;

import Config.DatabaseConnection;
import java.sql.Connection;

public class TestConexion {

    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("Conexion EXITOSA");
            }
        } catch (Exception e) {
            System.out.println("Error de conexion");
            e.printStackTrace();
        }
    }
}
