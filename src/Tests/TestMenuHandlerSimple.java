package Tests;

import Main.MenuHandler;
import Models.CodigoBarras;
import Models.Producto;
import Service.ProductoServiceImp;
import Service.CodigoBarrasServiceImp;
import DAO.ProductoDAO;
import DAO.CodigoBarrasDAO;
import Models.Categoria;
import java.util.Scanner;
import java.io.ByteArrayInputStream;
import java.util.Collections;

public class TestMenuHandlerSimple {

    public static void main(String[] args) {
        System.out.println("=== TEST SIMPLE MENU HANDLER ===");

        testConstructor();
        testCrearCodigoBarras();
        testListarProductosSimulado();
        testCrearProductoCompleto();
        testBusquedaProductos();
        testValidaciones();

        System.out.println("=== FIN TESTS ===");
    }

    public static void testConstructor() {
        System.out.println("\n1. Probando constructor...");

        try {
            // Esto debería fallar
            new MenuHandler(null, null);
            System.out.println("ERROR: Debio fallar con scanner null");
        } catch (IllegalArgumentException e) {
            System.out.println("OK: Constructor detecta scanner null - " + e.getMessage());
        }
    }

    public static void testCrearCodigoBarras() {
        System.out.println("\n2. Probando crear codigo de barras...");

        try {
            // Simular usuario escribiendo: "1234567890123" + ENTER + "1" + ENTER
            String inputSimulado = "1234567890123\n1\n";
            Scanner scanner = new Scanner(new ByteArrayInputStream(inputSimulado.getBytes()));

            // Crear servicios REALES para la prueba
            CodigoBarrasDAO cbDAO = new CodigoBarrasDAO();
            CodigoBarrasServiceImp cbService = new CodigoBarrasServiceImp(cbDAO);
            ProductoDAO pDAO = new ProductoDAO();
            ProductoServiceImp pService = new ProductoServiceImp(pDAO, cbService);

            MenuHandler handler = new MenuHandler(scanner, pService);

            CodigoBarras codigo = handler.crearCodigoBarras();

            if (codigo != null) {
                System.out.print("OK: Codigo creado - Valor: " + codigo.getValor() + ", Tipo: " + codigo.getTipoId() + ", idcodigo: " + codigo.getId());
                System.out.println("");
            } else {
                System.out.print("ERROR: Codigo es null");
                System.out.println("");
            }

        } catch (Exception e) {
            System.out.print("ERROR: " + e.getMessage());
            System.out.println("");
            // e.printStackTrace(); // Comentado para que no ensucie la salida
        }
    }

    public static void testListarProductosSimulado() {
        System.out.print("\n3. Probando listar productos (simulado)...");

        try {
            Scanner scanner = new Scanner("");

            // Crear servicios REALES
            CodigoBarrasDAO cbDAO = new CodigoBarrasDAO();
            CodigoBarrasServiceImp cbService = new CodigoBarrasServiceImp(cbDAO);
            ProductoDAO pDAO = new ProductoDAO();
            ProductoServiceImp pService = new ProductoServiceImp(pDAO, cbService);

            System.out.println("");
            MenuHandler handler = new MenuHandler(scanner, pService);
            handler.listarProductos();
            System.out.println("OK: Listar productos funciono");
            System.out.println("");

        } catch (Exception e) {
            System.out.println("ERROR en listar: " + e.getMessage());
        }
    }

    public static void testCrearProductoCompleto() {
        System.out.println("\n4. Probando crear producto COMPLETO...");
        System.out.println("");

        try {
            // Simular usuario escribiendo todos los datos:
            // "Producto Test", "100.0", "0.5", "2", "1", "1234567890123", "1"
            String inputSimulado = "Producto Test\n100.0\n0.5\n2\n1\n1234567890123\n1\n";
            Scanner scanner = new Scanner(new ByteArrayInputStream(inputSimulado.getBytes()));

            // Crear servicios REALES
            CodigoBarrasDAO cbDAO = new CodigoBarrasDAO();
            CodigoBarrasServiceImp cbService = new CodigoBarrasServiceImp(cbDAO);
            ProductoDAO pDAO = new ProductoDAO();
            ProductoServiceImp pService = new ProductoServiceImp(pDAO, cbService);

            MenuHandler handler = new MenuHandler(scanner, pService);

            System.out.println("Iniciando creacion de producto...");
            System.out.println("");
            handler.crearProducto();
            System.out.println("OK: Flujo de crear producto completado");
            System.out.println("");

        } catch (Exception e) {
            System.out.println("ERROR en crear producto: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void testBusquedaProductos() {
        System.out.println("\n5. Probando busqueda de productos...");

        try {
            // Buscar producto existente
            String inputSimulado = "Test\n";
            Scanner scanner = new Scanner(new ByteArrayInputStream(inputSimulado.getBytes()));

            CodigoBarrasDAO cbDAO = new CodigoBarrasDAO();
            CodigoBarrasServiceImp cbService = new CodigoBarrasServiceImp(cbDAO);
            ProductoDAO pDAO = new ProductoDAO();
            ProductoServiceImp pService = new ProductoServiceImp(pDAO, cbService);

            MenuHandler handler = new MenuHandler(scanner, pService);
            handler.buscarPorNombre();
            System.out.println("OK: Busqueda completada");

        } catch (Exception e) {
            System.out.println("ERROR en busqueda: " + e.getMessage());
        }
    }

    public static void testValidaciones() {
        System.out.println("\n6. Probando validaciones...");

        // Test 1: Precio negativo
        testCaso("Producto Test\n-50.0\n0.5\n2\n1\n", "Precio negativo");

        // Test 2: Categoria invalida
        testCaso("Producto Test\n100.0\n0.5\n99\n1\n", "Categoria invalida");

        // Test 3: Codigo barras muy corto
        testCaso("Producto Test\n100.0\n0.5\n2\n1\n123\n1\n", "Codigo muy corto");

        // Test 4: Nombre vacio
        testCaso("\n100.0\n0.5\n2\n1\n", "Nombre vacio");

        // Test 5: Peso negativo
        testCaso("Producto Test\n100.0\n-1.0\n2\n1\n", "Peso negativo");
    }

    private static void testCaso(String input, String descripcion) {
        try {
            Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
            CodigoBarrasDAO cbDAO = new CodigoBarrasDAO();
            CodigoBarrasServiceImp cbService = new CodigoBarrasServiceImp(cbDAO);
            ProductoDAO pDAO = new ProductoDAO();
            ProductoServiceImp pService = new ProductoServiceImp(pDAO, cbService);

            MenuHandler handler = new MenuHandler(scanner, pService);
            handler.crearProducto();
            System.out.println(descripcion + " - Debio fallar");
        } catch (Exception e) {
            System.out.println(descripcion + " - Error manejado: " + e.getMessage());
        }
    }
}
