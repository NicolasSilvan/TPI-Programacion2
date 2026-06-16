package entities;

public class Producto extends Base {
 
    private String nombre;
    private Double precio;
    private String descripcion;
    private int stock;
    private String imagen;
    private Boolean disponible;
 
    // relacion N-1 con Categoria (muchos productos pertenecen a una categoria)
    private Categoria categoria;
 
    public Producto() {
        super();
    }
 
    public Producto(Long id, String nombre, Double precio, String descripcion,
                    int stock, String imagen, Boolean disponible, Categoria categoria) {
        super(id);
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.stock = stock;
        this.imagen = imagen;
        this.disponible = disponible;
        this.categoria = categoria;
    }
    
    
}

