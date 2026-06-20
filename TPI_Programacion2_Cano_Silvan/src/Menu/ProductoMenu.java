package Menu;

import entities.Categoria;
import entities.Producto;
import exception.EntidadNoEncontradaException;
import exception.PrecioInvalidoException;
import exception.StockInvalidoException;
import service.CategoriaService;
import service.ProductoService;

import java.util.List;

public class ProductoMenu {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;
    private final LectorConsola lector;

    public ProductoMenu(ProductoService productoService, CategoriaService categoriaService, LectorConsola lector) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
        this.lector = lector;
    }

    public void mostrarMenu() throws EntidadNoEncontradaException, StockInvalidoException, PrecioInvalidoException {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- PRODUCTOS ---");
            System.out.println("1. Listar (todos)");
            System.out.println("2. Listar por categoria");
            System.out.println("3. Crear");
            System.out.println("4. Editar");
            System.out.println("5. Eliminar");
            System.out.println("0. Volver al menu principal");
            int opcion = lector.leerEntero("Seleccione: ");

            switch (opcion) {
                case 1 -> listar();
                case 2 -> listarPorCategoria();
                case 3 -> crear();
                case 4 -> editar();
                case 5 -> eliminar();
                case 0 -> salir = true;
                default -> System.out.println("Opcion fuera de rango.");
            }
        }
    }

    private void listar() {
        List<Producto> productos = productoService.listar();
        if (productos.isEmpty()) {
            System.out.println("No hay productos cargados.");
            return;
        }
        System.out.println("\n--- Listado de productos ---");
        for (Producto p : productos) {
            System.out.println(p);
        }
    }

    private void listarPorCategoria() {
        List<Categoria> categorias = categoriaService.listarCategoria();
        if (categorias.isEmpty()) {
            System.out.println("No hay categorias cargadas.");
            return;
        }
        System.out.println("\n--- Categorias disponibles ---");
        for (Categoria c : categorias) {
            System.out.println(c);
        }

        Long idCategoria = lector.leerLong("\nIngrese el ID de la categoria: ");
        List<Producto> productos = productoService.listarProductoPorCategoria(idCategoria);
        if (productos.isEmpty()) {
            System.out.println("No hay productos en esa categoria.");
            return;
        }
        System.out.println("\n--- Productos de la categoria ---");
        for (Producto p : productos) {
            System.out.println(p);
        }
    }

    private void crear() throws EntidadNoEncontradaException, PrecioInvalidoException, StockInvalidoException {
        System.out.println("\n--- Crear producto ---");

        List<Categoria> categorias = categoriaService.listarCategoria();
        if (categorias.isEmpty()) {
            System.out.println("No hay categorias cargadas. Debe crear una categoria antes de crear productos.");
            return;
        }
        System.out.println("--- Categorias disponibles ---");
        for (Categoria c : categorias) {
            System.out.println(c);
        }

        try {
            Long idCategoria = lector.leerLong("\nID de la categoria: ");
            Categoria categoria = categoriaService.buscarCategoriaPorId(idCategoria);

            String nombre = lector.leerTexto("Nombre: ");
            if (nombre.isBlank()) {
                System.out.println("Error: el nombre no puede estar vacio.");
                return;
            }
            String descripcion = lector.leerTexto("Descripcion: ");
            Double precio = lector.leerDouble("Precio: ");
            int stock = lector.leerEntero("Stock: ");
            String imagen = lector.leerTexto("URL/nombre de imagen: ");
            boolean disponible = lector.leerBooleano("Disponible?");

            Producto nuevo = productoService.crear(nombre, precio, descripcion, stock, imagen, disponible, categoria);
            System.out.println("Producto creado con exito. ID asignado: " + nuevo.getId());
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void editar() throws EntidadNoEncontradaException, StockInvalidoException, PrecioInvalidoException {
        listar();
        Long id = lector.leerLong("\nIngrese el ID del producto a editar: ");
        try {
            productoService.buscarProductoPorId(id); // valida existencia antes de pedir datos

            String nuevoNombre = lector.leerTextoOpcional("Nuevo nombre (Enter para no modificar): ");
            Double nuevoPrecio = lector.leerDoubleOpcional("Nuevo precio (Enter para no modificar): ");
            String nuevaDescripcion = lector.leerTextoOpcional("Nueva descripcion (Enter para no modificar): ");
            Integer nuevoStock = lector.leerEnteroOpcional("Nuevo stock (Enter para no modificar): ");
            String nuevaImagen = lector.leerTextoOpcional("Nueva imagen (Enter para no modificar): ");

            boolean cambiarDisponibilidad = lector.leerConfirmacion("Desea cambiar la disponibilidad?");
            Boolean nuevaDisponibilidad = null;
            if (cambiarDisponibilidad) {
                nuevaDisponibilidad = lector.leerBooleano("Disponible?");
            }

            boolean cambiarCategoria = lector.leerConfirmacion("Desea cambiar la categoria?");
            Categoria nuevaCategoria = null;
            if (cambiarCategoria) {
                List<Categoria> categorias = categoriaService.listarCategoria();
                for (Categoria c : categorias) {
                    System.out.println(c);
                }
                Long idCategoria = lector.leerLong("ID de la nueva categoria: ");
                nuevaCategoria = categoriaService.buscarCategoriaPorId(idCategoria);
            }

            productoService.editarProducto(id, nuevoNombre, nuevoPrecio, nuevaDescripcion,
                    nuevoStock, nuevaImagen, nuevaDisponibilidad, nuevaCategoria);
            System.out.println("Producto actualizado con exito.");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void eliminar() throws EntidadNoEncontradaException {
        listar();
        Long id = lector.leerLong("\nIngrese el ID del producto a eliminar: ");
        try {
            Producto producto = productoService.buscarProductoPorId(id);

            boolean confirmar = lector.leerConfirmacion("Confirma eliminar '" + producto.getNombre() + "'?");
            if (!confirmar) {
                System.out.println("Operacion cancelada.");
                return;
            }

            productoService.eliminarProducto(id);
            System.out.println("Producto eliminado con exito.");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}