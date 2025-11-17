/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Config.DatabaseConnection;
import DAO.ProductoDAO;
import Models.CodigoBarras;
import Models.Producto;
import java.sql.*;
import java.util.List;

/**
 *
 * @author notvo
 */
public class ProductoServiceImp implements GenericService<Producto> {

    private final ProductoDAO productoDAO; // mediante inyeccion 

    private final CodigoBarrasServiceImp codigoBarrasSeriviceImp;

    public ProductoServiceImp(ProductoDAO productoDAO, CodigoBarrasServiceImp codigoBarrasSeriviceImp) {
        if (productoDAO == null) {
            throw new IllegalArgumentException("ProductoDao no puede ser null");
        }
        if (codigoBarrasSeriviceImp == null) {
            throw new IllegalArgumentException("CodigoBarraServiceImp no puede ser null");
        }

        this.productoDAO = productoDAO;
        this.codigoBarrasSeriviceImp = codigoBarrasSeriviceImp;
    }

    public void crearProductoConCodigo(Producto producto, CodigoBarras codigoBarras)
            throws SQLException, IllegalArgumentException {
        validateProducto(producto);

        //transaccion
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try {
                if (codigoBarras != null) {
                    codigoBarrasSeriviceImp.validate(codigoBarras); // validator
                    codigoBarrasSeriviceImp.insertarTx(codigoBarras, conn); // insert con transaccion en codigobarrasserivceimp
                    producto.setCodigoBarras(codigoBarras);
                }

                insertarTx(producto, conn);
                conn.commit();
                conn.setAutoCommit(true);

            } catch (SQLException e) {
                System.err.println("Ocurrio un SQLException. Se hace rollback");
                conn.rollback();
                System.err.println("Rollback");
                throw e;

            } catch (Exception e) {
                System.err.println("Ocurrio un error no-SQL. Se hace rollback");
                conn.rollback(); 
                throw e;
            }
        }
    }

    @Override
    public void insertarTx(Producto producto, Connection conn) throws SQLException {
        productoDAO.crearTx(producto, conn);
    }

    // no implementado y quizas no lo implemente
    @Override
    public void insertar(Producto producto) throws SQLException {
        throw new UnsupportedOperationException("Use crearProductoConCodigo()");
    }

    @Override
    public void actualizar(Producto producto) throws SQLException {
        validateProducto(producto);
        if (producto.getId() <= 0) {
            throw new IllegalArgumentException("ID invalido para actualizar");
        } // esto podria estar en un metodo
        if (producto.getId() <= 0) {
            throw new IllegalArgumentException("El ID del producto debe ser mayor a 0 para actualizar");
        }
        productoDAO.actualizar(producto);
    }

    @Override
    public void eliminar(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor a 0");
        }
        productoDAO.eliminar(id);
    }

    @Override
    public Producto getById(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor a 0");
        }
        return productoDAO.leer(id);
    }

    @Override
    public List<Producto> getAll() throws SQLException {
        return productoDAO.leertodos();
    }

    // mas validaciones necesarias, con estas andan bien por ahora
    // revisar bien en la base de datos la desicion de que validar
    private void validateProducto(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("Producto no puede ser null");
        }
        if (producto.getPrecio() <= 0) {
            throw new IllegalArgumentException("Precio debe ser mayor a 0");
        }

    }

    public List<Producto> buscarPorNombre(String nombre) throws SQLException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de busqueda no puede estar vacio");
        }
        return productoDAO.buscarPorNombre(nombre);
    }

    public CodigoBarrasServiceImp getCodigoBarrasSeriviceImp() {
        return codigoBarrasSeriviceImp;
    }

}
