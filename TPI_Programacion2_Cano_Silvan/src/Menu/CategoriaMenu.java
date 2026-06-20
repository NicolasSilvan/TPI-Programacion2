package Menu;

import entities.Categoria;
import exception.EntidadNoEncontradaException;
import exception.NombreDuplicadoException;
import service.CategoriaService;
import service.ProductoService;

import java.util.List;

public class CategoriaMenu {
    private final CategoriaService categoriaService;
    private final ProductoService productoService;
    private final LectorConsola lector;
    
    public CategoriaMenu(CategoriaService categoriaService, ProductoService productoService, LectorConsola lector){
        this.categoriaService = categoriaService;
        this.productoService = productoService;
        this.lector = lector;
    }
    
    public void mostrarMenu() throws NombreDuplicadoException, EntidadNoEncontradaException {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- CATEGORIAS ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver al menu principal");
            int opcion = lector.leerEntero("Seleccione: ");
 
            switch (opcion) {
                case 1 -> listar();
                case 2 -> crear();
                case 3 -> editar();
                case 4 -> eliminar();
                case 0 -> salir = true;
                default -> System.out.println("Opcion fuera de rango.");
            }
        }
    }
    
    private void listar() {
        List<Categoria> categorias = categoriaService.listarCategoria();
        if (categorias.isEmpty()) {
            System.out.println("No hay categorias cargadas.");
            return;
        }
        System.out.println("\n--- Listado de categorias ---");
        for (Categoria c : categorias) {
            System.out.println(c);
        }
    }
    
    private void crear() throws NombreDuplicadoException {
        System.out.println("\n--- Crear categoria ---");
        String nombre = lector.leerTexto("Nombre: ");
        if (nombre.isBlank()) {
            System.out.println("Error, el nombre no puede estar vacio...");
            return;
        }
        String descripcion = lector.leerTexto("Descripcion: ");
        if (descripcion.isBlank()) {
            System.out.println("Error, la descripcion no puede estar vacia.");
            return;
        }
 
        try {
            Categoria nueva = categoriaService.crearCategoria(nombre, descripcion);
            System.out.println("Categoria creada con exito! ID : " + nueva.getId());
        } catch (RuntimeException e) {
            System.out.println("Error, " + e.getMessage());
        }
    }
 
    private void editar() throws EntidadNoEncontradaException, NombreDuplicadoException {
        listar();
        Long id = lector.leerLong("\nIngrese el ID de la categoria a editar: ");
        try {
            String nuevoNombre = lector.leerTextoOpcional("Nuevo nombre (Enter para no modificar): ");
            String nuevaDescripcion = lector.leerTextoOpcional("Nueva descripcion (Enter para no modificar): ");
 
            categoriaService.editar(id, nuevoNombre, nuevaDescripcion);
            System.out.println("Categoria actualizada con exito!");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
 
    private void eliminar() throws EntidadNoEncontradaException {
        listar();
        Long id = lector.leerLong("\nIngrese el ID de la categoria a eliminar: ");
        try {
            Categoria categoria = categoriaService.buscarCategoriaPorId(id);
 
            boolean tieneProductos = !productoService.listarProductoPorCategoria(id).isEmpty();
            if (tieneProductos) {
                System.out.println("Advertencia, esta categoria tiene productos asociados!");
            }
 
            boolean confirmar = lector.leerConfirmacion("Confirma eliminar '" + categoria.getNombre() + "'?");
            if (!confirmar) {
                System.out.println("Operacion cancelada!");
                return;
            }
 
            categoriaService.eliminar(id);
            System.out.println("Categoria eliminada con exito!");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
