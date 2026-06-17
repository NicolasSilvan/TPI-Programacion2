package entities;

import enums.Estado;
import enums.FormaPago;
import exception.EntidadNoEncontradaException;
import exception.PrecioInvalidoException;
import exception.StockInvalidoException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
 
public class Pedido extends Base implements Calculable {
 
    private LocalDate fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;
    
    private static Long contador = 0L;
 
    // Relacion N:1 con Usuario (muchos pedidos pertenecen a un usuario)
    private Usuario usuario;
 
    // Relacion de composicion 1:N con DetallePedido (los detalles viven dentro del pedido)
    private List<DetallePedido> detalles;
 
    public Pedido() {
        super();
        this.detalles = new ArrayList<>();
        this.total = 0.0;
        this.estado = Estado.PENDIENTE;
    }
 
    public Pedido(LocalDate fecha, Estado estado, FormaPago formaPago, Usuario usuario) {
        super(contador++);
        this.fecha = fecha;
        this.estado = estado;
        this.formaPago = formaPago;
        this.usuario = usuario;
        this.total = 0.0;
        this.detalles = new ArrayList<>();
    }
 
    // Metodos propios del UML
    public void addDetallePedido(int cantidad, Double precioUnitario, Producto producto) throws StockInvalidoException, PrecioInvalidoException{
        if (precioUnitario <= 0){
            throw new PrecioInvalidoException("El precio debe ser mayor a 0.");
        }
        if (cantidad <= 0){
            throw new StockInvalidoException("La cantidad debe ser mayor a 0.");
        }
        
        DetallePedido detalle = new DetallePedido(cantidad, precioUnitario, producto);
        detalles.add(detalle);
        calcularTotal();
    }
 
    public DetallePedido findeDetallePedidoByProducto(Producto producto) throws EntidadNoEncontradaException{
        for(DetallePedido d : detalles){
            if(d.getProducto().equals(producto)){
                return d;
            }
        }
        throw new EntidadNoEncontradaException("El hay ningun detalle con ese producto o no existe.");
    }
 
    public void deleteDetallePedidoByProducto(Producto producto) throws EntidadNoEncontradaException{
        boolean encontrado = false;
        for(DetallePedido d : detalles){
            if(d.getProducto().equals(producto)){
                d.setEliminado(true);
                encontrado = true;
            }
        }
        if(encontrado == false){
            throw new EntidadNoEncontradaException("No hay ningun detalle con ese producto o no existe.");
        }
        calcularTotal();
    }
 
    // Metodo de la interfaz Calculable
    @Override
    public void calcularTotal() {
        for (DetallePedido d : detalles){
            if(d.getEliminado() == false){
                total += d.getSubtotal();
            }
        }
    }
    
    @Override
    public String toString() {
        return "Pedido {\n - ID : " + this.getId() + "\n - Usuario : " + this.usuario.getNombre() + 
                "\n - Fecha : " + this.fecha + "\n - Estado : " + this.estado + "\n - Forma de Pago : " + this.formaPago +
                "\n - Total : $" + this.total + "\n - Items : " + detalles.size() + "\n}";
    }
}
