package Main;

import Models.Categoria;
import Models.CodigoBarras;
import Models.Marca;
import Models.Producto;
import Models.TipoCodigoBarras;
import Service.CodigoBarrasServiceImp;
import Service.ProductoServiceImp;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Fernando
 */
public class MenuHandler {

    /**
     * Scanner compartido para leer entrada del usuario. Inyectado desde AppMenu
     * para evitar m�ltiples Scanners de System.in.
     */
    private final Scanner scanner;

    private final ProductoServiceImp productoService;

    public MenuHandler(Scanner scanner, ProductoServiceImp productoService) {
        if (scanner == null) {
            throw new IllegalArgumentException("Scanner no puede ser null");
        }
        if (productoService == null) {
            throw new IllegalArgumentException("PersonaService no puede ser null");
        }
        this.scanner = scanner;
        this.productoService = productoService;
    }

    public void crearProducto() {
        try {
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine().trim();
            if (nombre.isEmpty()) {
                throw new IllegalArgumentException("El nombre no puede estar vacio.");
            }

            // Validar el precio
            System.out.print("Precio: ");
            double precio = Double.parseDouble(scanner.nextLine().trim());
            if (precio <= 0) {
                throw new IllegalArgumentException("El precio debe ser mayor a 0.");
            }

            // Validar el peso
            System.out.print("Peso: ");
            double peso = Double.parseDouble(scanner.nextLine().trim());
            if (peso <= 0) {
                throw new IllegalArgumentException("El peso debe ser mayor a 0.");
            }

            // Validar el ID de la categoría
            Categoria.mostrarOpciones();
            System.out.print("Id Categoria: ");
            int idCategoria = Integer.parseInt(scanner.nextLine().trim());
            Categoria.buscarPorId(idCategoria);

            // Validar el ID de la marca
            Marca.mostrarOpciones();
            System.out.print("Id Marca: ");
            int idMarca = Integer.parseInt(scanner.nextLine().trim());
            Marca.buscarPorId(idMarca);

            CodigoBarras codigoBarras = crearCodigoBarras();

            Producto producto = new Producto(nombre, precio, peso, idCategoria, idMarca, codigoBarras); // aca usamos el getid de cogiobarras porque no lo recibimos a mano, es algo que nos da sql en este caso
            
            productoService.crearProductoConCodigo(producto,codigoBarras);
            
            System.out.println("Producto creado exitosamente con ID: " + producto.getId());
        } catch (Exception e) {
            System.err.println("Error al crear producto: " + e.getMessage());
        }
    }

    public void listarProductos() {
        try {

            List<Producto> productos;
            productos = productoService.getAll();

            if (productos.isEmpty()) {
                System.out.println("No se encontraron productos.");

            } else {

                for (Producto p : productos) {
                    System.out.println(p);
                    System.out.println("====================");
                }

            }
        } catch (Exception e) {
            System.err.println("Error al listar productos: " + e.getMessage());
        }
    }

    /*
      Permite actualizar el precio del producto
     */
    public void actualizarProducto() {
        try {
            System.out.print("ID del producto a actualizar: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            Producto p = productoService.getById(id);

            if (p == null) {
                System.out.println("Producto no encontrado.");
                return;
            }

            System.out.print("Nuevo precio (actual: " + p.getPrecio() + ", Enter para mantener): ");
            String inputPrecio = scanner.nextLine().trim();
            if (!inputPrecio.isEmpty()) {
                try {
                    double precio = Double.parseDouble(inputPrecio);
                    p.setPrecio(precio);
                } catch (NumberFormatException e) {
                    System.out.println("El precio ingresado no es valido");
                }
            }

            productoService.actualizar(p);
            System.out.println("Producto actualizada exitosamente.");
        } catch (Exception e) {
            System.err.println("Error al actualizar producto: " + e.getMessage());
        }
    }

    public void eliminarProducto() {
        try {
            System.out.print("ID del producto a eliminar: ");
            int id = Integer.parseInt(scanner.nextLine());
            productoService.eliminar(id);
            System.out.println("Persona eliminada exitosamente.");
        } catch (Exception e) {
            System.err.println("Error al eliminar persona: " + e.getMessage());
        }
    }
        
        
        public void buscarPorNombre(){
            List<Producto> productos;
            try{
                System.out.print("Ingrese texto a buscar: ");
                String nombre = scanner.nextLine().trim();
                productos = productoService.buscarPorNombre(nombre);
                
                if (!productos.isEmpty()) {
                    for (Producto p : productos) {
                        System.out.println(p);
                    }
                }else{
                    System.out.println("No se encontraron productos con ese nombre.");
                }
            }catch(SQLException e) {
                System.out.println("Error de base de datos al buscar productos." + e.getMessage());
            }catch(Exception ex){
                System.out.println("Error al realizar la búsqueda" + ex.getMessage());
            }
            
        }
        
        
        public CodigoBarras crearCodigoBarras(){
             try{
                // Solicitar el valor del código de barras
                System.out.print("Valor del codigo de barras(8 - 20 digitos): ");
                String valor = scanner.nextLine().trim();

                if (valor.isEmpty() || valor.length() < 8 || valor.length() > 20 ) {
                    throw new IllegalArgumentException("El valor del codigo de barras debe tener entre 8 y 20 digitos.");
                }

            // Solicitar el tipo de código de barra 
            TipoCodigoBarras.mostrarOpciones();
            System.out.print("Seleccionar tipo: ");
            int tipoId = Integer.parseInt(scanner.nextLine().trim());
            // Validar el rango entre 1 y 3
            TipoCodigoBarras.buscarPorId(tipoId);

            // Crear el objeto CodigoBarras
            CodigoBarras codigoBarras = new CodigoBarras(valor, tipoId);

            return codigoBarras;

        } catch (Exception e) {
            System.err.println("Error al crear el codigo de barras: " + e.getMessage());
            return null;  // En caso de error, retorna null 
        }
    }


    
    public void buscarPorId(){
            try{
                System.out.print("Ingrese id del producto a buscar: ");
                int id = Integer.parseInt(scanner.nextLine().trim());
                Producto producto = productoService.getById(id);
                
                if (producto != null){
                    System.out.println("====================");
                    System.out.println(producto);
                    System.out.println("====================");
                    
                }else{
                    System.out.println("No se encontro el producto con id " + id);
                }
            }catch(SQLException e) {
                System.out.println("Error de base de datos al buscar productos." + e.getMessage());
            }catch(Exception ex){
                System.out.println("Error al realizar la busqueda" + ex.getMessage());
            }
            
         
     }

}
