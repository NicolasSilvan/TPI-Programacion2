package service;

import entities.Pedido;
import entities.Producto;
import entities.Usuario;
import enums.Estado;
import enums.FormaPago;
import exception.EntidadNoEncontradaException;
 
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PedidoService {
    private final List<Pedido> pedidos = new ArrayList<>();
    private Long contadorId = 1L;
    
    //listar pedidos no eliminados
    public List<Pedido> listarPedidos() {
        List<Pedido> activos = new ArrayList<>();
        for (Pedido p : pedidos) {
            if (!p.getEliminado()) {
                activos.add(p);
            }
        }
        return activos;
    }
    
    //listar pedidos no eliminados de un usuario especifico
    public List<Pedido> listarPedidosPorUsuario(Long idUsuario) {
        List<Pedido> resultado = new ArrayList<>();
        for (Pedido p : pedidos) {
            if (!p.getEliminado()
                    && p.getUsuario() != null
                    && p.getUsuario().getId().equals(idUsuario)) {
                resultado.add(p);
            }
        }
        return resultado;
    }
    
    //buscar pedido por id
    public Pedido buscarPedidoPorId(Long id) throws EntidadNoEncontradaException {
        for (Pedido p : pedidos) {
            if (p.getId().equals(id) && !p.getEliminado()) {
                return p;
            }
        }
        throw new EntidadNoEncontradaException("No se encontro el pedido con ID: " + id);
    }
    
    //crear pedido
    public Pedido crearPedido(Usuario usuario, FormaPago formaPago) throws EntidadNoEncontradaException {
        if (usuario == null) {
            throw new EntidadNoEncontradaException("No se puede crear un pedido sin usuario.");
        }
 
        Pedido nuevo = new Pedido(LocalDate.now(), Estado.PENDIENTE, formaPago, usuario);
        pedidos.add(nuevo);
        return nuevo;
    }
    
    //agregar detalle a pedido existente
    public void agregarDetalle(Pedido pedido, int cantidad, Producto producto) throws Exception {
        try {
            pedido.addDetallePedido(cantidad, producto.getPrecio(), producto);
        } catch (Exception e) {
            // Cancelar el pedido en memoria si falla el primer detalle
            pedidos.remove(pedido);
            throw e;
        }
    }
    
    //actualiza el estado y/o forma de pago de un pedido
    public Pedido actualizarEstadoPedido(Long id, Estado nuevoEstado, FormaPago nuevaFormaPago) throws EntidadNoEncontradaException {
        Pedido pedido = buscarPedidoPorId(id);
 
        if (nuevoEstado != null) {
            pedido.setEstado(nuevoEstado);
        }
        if (nuevaFormaPago != null) {
            pedido.setFormaPago(nuevaFormaPago);
        }
        return pedido;
    }
    
    //marcar Pedido como eliminado
    public void eliminarPedido(Long id) throws EntidadNoEncontradaException {
        Pedido pedido = buscarPedidoPorId(id);
        pedido.setEliminado(true);
 
        // Soft delete en cascada a los detalles
        for (var detalle : pedido.getDetalles()) {
            detalle.setEliminado(true);
        }
    }
}
