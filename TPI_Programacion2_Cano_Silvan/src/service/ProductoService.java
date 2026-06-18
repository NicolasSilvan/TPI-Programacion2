package service;

import entities.Categoria;
import entities.Producto;
import exception.EntidadNoEncontradaException;
import exception.PrecioInvalidoException;
import exception.StockInvalidoException;
 
import java.util.ArrayList;
import java.util.List;

public class ProductoService {
    private final List<Producto> productos = new ArrayList<>();
    private Long contadorId = 1L;
    
    //devuelve productos no eliminados
    public List<Producto> listar() {
        List<Producto> activos = new ArrayList<>();
        for (Producto p : productos) {
            if (!p.getEliminado()) {
                activos.add(p);
            }
        }
        return activos;
    }
    
    //retorna productos no eliminados de una categoria especifica
    public List<Producto> listarProductoPorCategoria(Long idCategoria) {
        List<Producto> resultado = new ArrayList<>();
        for (Producto p : productos) {
            if (!p.getEliminado()
                    && p.getCategoria() != null
                    && p.getCategoria().getId().equals(idCategoria)) {
                resultado.add(p);
            }
        }
        return resultado;
    }
    
    //buscar producto por id
    public Producto buscarProductoPorId(Long id) throws EntidadNoEncontradaException {
        for (Producto p : productos) {
            if (p.getId().equals(id) && !p.getEliminado()) {
                return p;
            }
        }
        throw new EntidadNoEncontradaException("No se encontro el producto con ID: " + id);
    }
    
    //crear producto validando precio y stock
    public Producto crear(String nombre, Double precio, String descripcion,
                          int stock, String imagen, Boolean disponible, Categoria categoria) throws PrecioInvalidoException, StockInvalidoException {
        validarPrecio(precio);
        validarStock(stock);
 
        Producto nuevo = new Producto(contadorId++, nombre, precio, descripcion,
                                      stock, imagen, disponible, categoria);
        productos.add(nuevo);
        return nuevo;
    }
    
    //editar los campos nulos del producto
    public Producto editarProducto(Long id, String nuevoNombre, Double nuevoPrecio,
                           String nuevaDescripcion, Integer nuevoStock,
                           String nuevaImagen, Boolean nuevoDisponible, Categoria nuevaCategoria) throws EntidadNoEncontradaException, StockInvalidoException, PrecioInvalidoException {
        Producto producto = buscarProductoPorId(id);
 
        if (nuevoNombre != null && !nuevoNombre.isBlank()) {
            producto.setNombre(nuevoNombre);
        }
        if (nuevoPrecio != null) {
            validarPrecio(nuevoPrecio);
            producto.setPrecio(nuevoPrecio);
        }
        if (nuevaDescripcion != null && !nuevaDescripcion.isBlank()) {
            producto.setDescripcion(nuevaDescripcion);
        }
        if (nuevoStock != null) {
            validarStock(nuevoStock);
            producto.setStock(nuevoStock);
        }
        if (nuevaImagen != null && !nuevaImagen.isBlank()) {
            producto.setImagen(nuevaImagen);
        }
        if (nuevoDisponible != null) {
            producto.setDisponible(nuevoDisponible);
        }
        if (nuevaCategoria != null) {
            producto.setCategoria(nuevaCategoria);
        }
        return producto;
    }
    
    //marca producto como eliminado
    public void eliminarProducto(Long id) throws EntidadNoEncontradaException {
        Producto producto = buscarProductoPorId(id);
        producto.setEliminado(true);
    }
    
    //validacion de precio
    private void validarPrecio(Double precio) throws PrecioInvalidoException {
        if (precio < 0) {
            throw new PrecioInvalidoException("El precio no puede ser negativo. Valor ingresado: " + precio);
        }
    }
 
    //validacion de stock
    private void validarStock(int stock) throws StockInvalidoException {
        if (stock < 0) {
            throw new StockInvalidoException("El stock no puede ser negativo. Valor ingresado: " + stock);
        }
    }
}
