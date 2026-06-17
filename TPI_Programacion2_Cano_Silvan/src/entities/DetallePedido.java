package entities;

public class DetallePedido extends Base {
 
    private int cantidad;
    private double subtotal;
    
    private static Long contador = 0L;
 
    // Relacion N:1 con Producto (muchos detalles referencian un producto)
    private Producto producto;
 
    public DetallePedido() {
        super();
    }
    
    public DetallePedido(int cantidad, double precio, Producto producto) {
        super(contador++);
        this.setCantidad(cantidad);
        this.setProducto(producto);
        this.setSubtotal(cantidad*precio);
    }

    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
    
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
    
    public int getCantidad() { return this.cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; } 
    
    @Override
    public String toString() {
        return "DetallePedido {\n - ID : " + this.getId() + "\n - Producto : " + this.producto.getNombre() +
                "\n - Cantidad : " + this.cantidad + "\n - Subtotal : $" + this.subtotal + "\n}";
    }
}
