/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.CodigoBarrasDAO;
import Models.CodigoBarras;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author notvo
 */
public class CodigoBarrasServiceImp implements GenericService<CodigoBarras> {

    //private final CodigoBarras codigoBarras;
    private final CodigoBarrasDAO codigoBarrasDAO;


    public CodigoBarrasServiceImp(CodigoBarrasDAO codigoBarrasDAO) {
        if (codigoBarrasDAO == null) {
            throw new IllegalArgumentException("codigoBarrasDAO no puede ser null");
        }
        this.codigoBarrasDAO = codigoBarrasDAO;
    }
    
    @Override
    public void insertar(CodigoBarras codigoBarras) throws SQLException {
        throw new UnsupportedOperationException("Usar insertarTx");
    }

    @Override
    public void insertarTx(CodigoBarras codigoBarras, Connection conn) throws SQLException {
        codigoBarrasDAO.crearTx(codigoBarras, conn);
    }

    @Override
    public void actualizar(CodigoBarras codigoBarras) throws SQLException {
        validate(codigoBarras);
        if (codigoBarras.getId() <= 0) {
            throw new IllegalArgumentException("El ID del codigobarra debe ser mayor a 0 para actualizar");
        }
        codigoBarrasDAO.actualizar(codigoBarras);
    }

    @Override
    public void eliminar(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor a 0");
        }
        codigoBarrasDAO.eliminar(id);
    }

    @Override
    public CodigoBarras getById(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor a 0");
        }
        return codigoBarrasDAO.leer(id);
    }

    @Override
    public List<CodigoBarras> getAll() throws SQLException {
        return codigoBarrasDAO.leertodos();
    }

    public void validate(CodigoBarras codigoBarras) throws SQLException {

        if (codigoBarras == null) {
            throw new IllegalArgumentException("El codigo de barras no puede ser null");
        }

        if (codigoBarras.getId() < 0) {
            throw new IllegalArgumentException("El id del codigobarras debe ser positivo");

        }

        String valor = codigoBarras.getValor();

        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El numero de codigo de barras es obligatorio");
        }

        if (valor.length() < 8) {   // largo X
            throw new IllegalArgumentException("El codigo de barras debe tener al menos 8 digitos");
        }

        // VALIDACION ONLINE de valor del codigo de barras
   //     if (codigoBarrasDAO.existe(valor)) {
     //       throw new SQLException("El codigo de barras ya existe en el sistema");
     //   }
    }

}
