package Menu;

import entities.Pedido;
import entities.Producto;
import entities.Usuario;
import enums.Estado;
import enums.FormaPago;
import exception.EntidadNoEncontradaException;
import service.PedidoService;
import service.ProductoService;
import service.UsuarioService;

import java.util.List;

public class PedidoMenu {

    private final PedidoService pedidoService;
    private final UsuarioService usuarioService;
    private final ProductoService productoService;
    private final LectorConsola lector;

    public PedidoMenu(PedidoService pedidoService, UsuarioService usuarioService,
                      ProductoService productoService, LectorConsola lector) {
        this.pedidoService = pedidoService;
        this.usuarioService = usuarioService;
        this.productoService = productoService;
        this.lector = lector;
    }

    public void mostrarMenu() throws Exception {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- PEDIDOS ---");
            System.out.println("1. Listar (todos)");
            System.out.println("2. Listar por usuario");
            System.out.println("3. Crear pedido con detalles");
            System.out.println("4. Actualizar estado/forma de pago");
            System.out.println("5. Eliminar");
            System.out.println("0. Volver al menu principal");
            int opcion = lector.leerEntero("Seleccione: ");

            switch (opcion) {
                case 1 -> listar();
                case 2 -> listarPorUsuario();
                case 3 -> crearPedido();
                case 4 -> actualizarEstado();
                case 5 -> eliminar();
                case 0 -> salir = true;
                default -> System.out.println("Opcion fuera de rango.");
            }
        }
    }

    private void listar() {
        List<Pedido> pedidos = pedidoService.listarPedidos();
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos cargados.");
            return;
        }
        System.out.println("\n--- Listado de pedidos ---");
        for (Pedido p : pedidos) {
            System.out.println(p);
        }
    }

    private void listarPorUsuario() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios cargados.");
            return;
        }
        for (Usuario u : usuarios) {
            System.out.println(u);
        }
        Long idUsuario = lector.leerLong("\nIngrese el ID del usuario: ");
        List<Pedido> pedidos = pedidoService.listarPedidosPorUsuario(idUsuario);
        if (pedidos.isEmpty()) {
            System.out.println("Este usuario no tiene pedidos.");
            return;
        }
        System.out.println("\n--- Pedidos del usuario ---");
        for (Pedido p : pedidos) {
            System.out.println(p);
        }
    }

    private void crearPedido() throws EntidadNoEncontradaException, Exception {
        System.out.println("\n--- Crear pedido ---");

        List<Usuario> usuarios = usuarioService.listarUsuarios();
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios cargados. Debe crear un usuario antes de crear pedidos.");
            return;
        }
        System.out.println("--- Usuarios disponibles ---");
        for (Usuario u : usuarios) {
            System.out.println(u);
        }

        Usuario usuario;
        try {
            Long idUsuario = lector.leerLong("\nID del usuario: ");
            usuario = usuarioService.buscarUsuarioPorId(idUsuario);
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        FormaPago formaPago = seleccionarFormaPago();

        Pedido pedido;
        try {
            pedido = pedidoService.crearPedido(usuario, formaPago);
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        // carga de detalles (1..N) usando obligatoriamente addDetallePedido()
        boolean seguirAgregando = true;
        boolean alMenosUnDetalle = false;

        while (seguirAgregando) {
            List<Producto> productos = productoService.listar();
            if (productos.isEmpty()) {
                System.out.println("No hay productos cargados... No se puede continuar con el pedido.");
                break;
            }
            System.out.println("\n--- Productos disponibles ---");
            for (Producto p : productos) {
                System.out.println(p);
            }

            try {
                Long idProducto = lector.leerLong("\nID del producto a agregar: ");
                Producto producto = productoService.buscarProductoPorId(idProducto);
                int cantidad = lector.leerEntero("Cantidad: ");

                pedidoService.agregarDetalle(pedido, cantidad, producto);
                alMenosUnDetalle = true;
                System.out.println("Detalle agregado. Subtotal acumulado: $" + pedido.getTotal());

            } catch (RuntimeException e) {
                // HU-PED-02: si falla un detalle, se cancela el pedido en memoria
                System.out.println("Error: " + e.getMessage());
                System.out.println("Se cancela la creacion del pedido para evitar datos inconsistentes.");
                return;
            }

            seguirAgregando = lector.leerConfirmacion("Desea agregar otro producto?");
        }

        if (!alMenosUnDetalle) {
            System.out.println("El pedido no tiene detalles cargados. Se cancela la operacion.");
            pedidoService.eliminarPedido(pedido.getId());
            return;
        }

        System.out.println("Pedido creado con exito. ID : " + pedido.getId() + " | Total: $" + pedido.getTotal());
    }

    private void actualizarEstado() throws EntidadNoEncontradaException {
        listar();
        Long id = lector.leerLong("\nIngrese el ID del pedido a actualizar: ");
        try {
            pedidoService.buscarPedidoPorId(id); // valida existencia

            boolean cambiarEstado = lector.leerConfirmacion("Desea cambiar el estado?");
            Estado nuevoEstado = null;
            if (cambiarEstado) {
                nuevoEstado = seleccionarEstado();
            }

            boolean cambiarFormaPago = lector.leerConfirmacion("Desea cambiar la forma de pago?");
            FormaPago nuevaFormaPago = null;
            if (cambiarFormaPago) {
                nuevaFormaPago = seleccionarFormaPago();
            }

            pedidoService.actualizarEstadoPedido(id, nuevoEstado, nuevaFormaPago);
            System.out.println("Pedido actualizado con exito.");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void eliminar() throws EntidadNoEncontradaException {
        listar();
        Long id = lector.leerLong("\nIngrese el ID del pedido a eliminar: ");
        try {
            Pedido pedido = pedidoService.buscarPedidoPorId(id);

            boolean confirmar = lector.leerConfirmacion("Confirma eliminar el pedido Nro" + pedido.getId() + "?");
            if (!confirmar) {
                System.out.println("Operacion cancelada.");
                return;
            }

            pedidoService.eliminarPedido(id);
            System.out.println("Pedido eliminado con exito.");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private FormaPago seleccionarFormaPago() {
        System.out.println("Seleccione forma de pago:");
        FormaPago[] formas = FormaPago.values();
        for (int i = 0; i < formas.length; i++) {
            System.out.println((i + 1) + ". " + formas[i]);
        }
        int opcion = lector.leerEntero("Opcion: ");
        while (opcion < 1 || opcion > formas.length) {
            System.out.println("Opcion invalida.");
            opcion = lector.leerEntero("Opcion: ");
        }
        return formas[opcion - 1];
    }

    private Estado seleccionarEstado() {
        System.out.println("Seleccione estado:");
        Estado[] estados = Estado.values();
        for (int i = 0; i < estados.length; i++) {
            System.out.println((i + 1) + ". " + estados[i]);
        }
        int opcion = lector.leerEntero("Opcion: ");
        while (opcion < 1 || opcion > estados.length) {
            System.out.println("Opcion invalida.");
            opcion = lector.leerEntero("Opcion: ");
        }
        return estados[opcion - 1];
    }
}