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
        this.setNombre(nombre);
        this.setDescripcion(descripcion);
        this.productos = new ArrayList<>();
    }

    public String getNombre() { return this.nombre; }
    public void setNombre(String nombre) { 
        if(nombre != null && !nombre.isEmpty()){
            this.nombre = nombre;
        } else {
            System.out.println("El nombre de la Categoria no puede ser vacio, agregando nombre generico...");
            this.nombre = "Categoria_" + super.getId();
        }
    }
    
    public String getDescripcion() { return this.descripcion; }
    public void setDescripcion(String descripcion) {
        if(descripcion != null && !descripcion.isEmpty()) {
            this.descripcion = descripcion;
        } else {
            this.descripcion = "-------";
        }
    }
    
    public List<Producto> getProductos() { return this.productos; }
    public void setProductos(List<Producto> productos) { this.productos = productos; }
    
    @Override
    public String toString(){
        return "Categoria {\n - ID : " + this.getId() + "\n - Nombre : " + this.nombre + 
                "\n - Descripcion : " + this.descripcion + "\n}";
    }
}
