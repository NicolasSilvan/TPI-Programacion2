package entities;

public class DetallePedido extends Base {
 
    private int cantidad;
    private Double subtotal;
    
    private static Long contador = 0L;
 
    // Relacion N:1 con Producto (muchos detalles referencian un producto)
    private Producto producto;
 
    public DetallePedido() {
        super();
    }
    
    public DetallePedido(int cantidad, Double precio, Producto producto) {
        super(contador++);
        this.cantidad = cantidad;
        this.producto = producto;
        this.subtotal = cantidad*precio;
    }

    public Double getSubtotal() {
        return subtotal;
    }
    
    public Producto getProducto() {
        return producto;
    }
}
