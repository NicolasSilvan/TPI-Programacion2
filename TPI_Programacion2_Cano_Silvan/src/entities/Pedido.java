package entities;

import enums.Estado;
import enums.FormaPago;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
 
public class Pedido extends Base implements Calculable {
 
    private LocalDate fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;
 
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
 
    public Pedido(Long id, LocalDate fecha, Estado estado, FormaPago formaPago, Usuario usuario) {
        super(id);
        this.fecha = fecha;
        this.estado = estado;
        this.formaPago = formaPago;
        this.usuario = usuario;
        this.total = 0.0;
        this.detalles = new ArrayList<>();
    }
 
    // Metodos propios del UML
    public void addDetallePedido(int cantidad, Double precioUnitario, Producto producto) {
        // TODO
    }
 
    public DetallePedido findeDetallePedidoByProducto(Producto producto) {
        // TODO
        return null;
    }
 
    public void deleteDetallePedidoByProducto(Producto producto) {
        // TODO
    }
 
    // Metodo de la interfaz Calculable
    @Override
    public void calcularTotal() {
        // TODO
    }
}
