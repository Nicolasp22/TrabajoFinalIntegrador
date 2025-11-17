/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tests;

import DAO.CodigoBarrasDAO;
import Service.ProductoServiceImp;
import DAO.ProductoDAO;
import Models.Producto;
import Models.CodigoBarras;
import Service.CodigoBarrasServiceImp;

/**
 *
 * @author notvo
 */
public class TestProductoServiceImp {

public static void main(String[] args) {
    try {
        ProductoDAO pdao = new ProductoDAO();
        CodigoBarrasServiceImp cbservice = new CodigoBarrasServiceImp(new CodigoBarrasDAO());
        ProductoServiceImp service = new ProductoServiceImp(pdao, cbservice);

        // Prueba manual - SIN ID (se genera automáticamente)
        Producto p = new Producto();
        p.setNombre("Galletitas");
        p.setPrecio(100);
        p.setPeso(0.5);
        p.setIdMarca(1);      // ← Número de marca (ej: 1 = "Arcor")
        p.setIdCategoria(2);  // ← Número de categoría (ej: 2 = "Alimentos")
        
        CodigoBarras cb = new CodigoBarras();
        cb.setValor("12345678");
        cb.setTipoId(1);      // ← Tipo de código (1, 2, o 3)

        service.crearProductoConCodigo(p, cb);

        System.out.println("OK: Producto creado con ID: " + p.getId());
        System.out.println("Código de barras creado con ID: " + cb.getId());

    } catch (Exception e) {
        System.err.println("Error: " + e.getMessage());
        e.printStackTrace();
    }
}
}
