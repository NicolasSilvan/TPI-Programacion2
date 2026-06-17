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
        this.setNombre(nombre);
        this.setPrecio(precio);
        this.setDescripcion(descripcion);
        this.setStock(stock);
        this.setImagen(imagen);
        this.setDisponible(disponible);
        this.setCategoria(categoria);
    }
    
    public String getNombre() { return this.nombre; }
    public void setNombre(String nombre){
        if(nombre != null && !nombre.isEmpty()) {
            this.nombre = nombre;
        } else {
            System.out.println("El nombre del producto no puede ser vacio, generando nombre generico...");
            this.nombre = "Producto_" + super.getId();
        }
    }
    
    public double getPrecio() { return this.precio; }
    public void setPrecio(double precio) {
        if(precio > 0) {
            this.precio = precio;
        } else {
            System.out.println("El precio no puede ser 0 o negativo, seteado en 1.0 ...");
            this.precio = 1.0;
        }
    }
    
    public String getDescripcion() { return this.descripcion; }
    public void setDescripcion(String descripcion) {
        if(descripcion != null && !descripcion.isEmpty()){
            this.descripcion = descripcion;
        } else {
            this.descripcion = "-------";
        }
    }
    
    public int getStock() { return this.stock; }
    public void setStock(int stock) {
        if(stock >= 0){
            this.stock = stock;
        } else {
            System.out.println("El stock no puede ser negativo, seteado en 0...");
            this.stock = 0;
        }
    }
    
    public String getImagen() { return this.imagen; }
    public void setImagen(String imagen) {
        if(imagen != null && !imagen.isEmpty()) {
            this.imagen = imagen;
        } else {
            this.imagen = "-------";
        }
    }
    
    public boolean getDisponible() { return this.disponible; }
    public void setDisponible(Boolean disponible) { this.disponible = disponible; }
    
    public Categoria getCategoria() { return this.categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria;}
    
    @Override
    public String toString() {
        return "Producto {\n - ID : " + this.getId() + "\n - Nombre : " + this.nombre + 
                "\n - Descripcion : " + this.descripcion + "\n - Precio : $" + this.precio + 
                "\n - Stock : " + this.stock + "\n - Disponible : " + (disponible ? "Si" : "No") + 
                "\n - Categoria : " + (this.categoria != null ? this.categoria.getNombre() : "Sin categoria") + 
                "\n}";
    }
}

