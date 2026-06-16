package entities;

import java.util.ArrayList;
import java.util.List;
 
public class Categoria extends Base {
 
    private String nombre;
    private String descripcion;
 
    // Relacion 1:N con Producto (una categoria tiene muchos productos)
    private List<Producto> productos;
 
    public Categoria() {
        super();
        this.productos = new ArrayList<>();
    }
 
    public Categoria(Long id, String nombre, String descripcion) {
        super(id);
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.productos = new ArrayList<>();
    }


}
