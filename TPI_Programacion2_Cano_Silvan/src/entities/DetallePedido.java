package entities;

public class DetallePedido extends Base {
 
    private int cantidad;
    private Double subtotal;
 
    // Relacion N:1 con Producto (muchos detalles referencian un producto)
    private Producto producto;
 
    public DetallePedido() {
        super();
    }
 
    public DetallePedido(Long id, int cantidad, Double subtotal, Producto producto) {
        super(id);
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.producto = producto;
    }
}
