
import Menu.CategoriaMenu;
import Menu.LectorConsola;
import Menu.PedidoMenu;
import Menu.ProductoMenu;
import Menu.UsuarioMenu;
import exception.EntidadNoEncontradaException;
import exception.MailDuplicadoException;
import exception.NombreDuplicadoException;
import exception.PrecioInvalidoException;
import exception.StockInvalidoException;
import java.util.Scanner;
import service.CategoriaService;
import service.PedidoService;
import service.ProductoService;
import service.UsuarioService;

public class Main {
    public static void main(String[] args) throws NombreDuplicadoException, PrecioInvalidoException, StockInvalidoException, MailDuplicadoException, EntidadNoEncontradaException, Exception {
        // servicios
        CategoriaService categoriaService = new CategoriaService();
        ProductoService productoService = new ProductoService();
        UsuarioService usuarioService = new UsuarioService();
        PedidoService pedidoService = new PedidoService();
 
        // Scanner unico compartido por toda la aplicacion
        Scanner scanner = new Scanner(System.in);
        LectorConsola lector = new LectorConsola(scanner);
 
        // Submenus con services necesarios
        CategoriaMenu categoriaMenu = new CategoriaMenu(categoriaService, productoService, lector);
        ProductoMenu productoMenu = new ProductoMenu(productoService, categoriaService, lector);
        UsuarioMenu usuarioMenu = new UsuarioMenu(usuarioService, lector);
        PedidoMenu pedidoMenu = new PedidoMenu(pedidoService, usuarioService, productoService, lector);
 
        // datos de prueba 
        cargarDatosDePrueba(categoriaService, productoService, usuarioService);
 
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== SISTEMA DE PEDIDOS (FOOD STORE) ===");
            System.out.println("1. Categorias");
            System.out.println("2. Productos");
            System.out.println("3. Usuarios");
            System.out.println("4. Pedidos");
            System.out.println("0. Salir");
            int opcion = lector.leerEntero("Seleccione: ");
 
            switch (opcion) {
                case 1 -> categoriaMenu.mostrarMenu();
                case 2 -> productoMenu.mostrarMenu();
                case 3 -> usuarioMenu.mostrarMenu();
                case 4 -> pedidoMenu.mostrarMenu();
                case 0 -> {
                    salir = true;
                    System.out.println("Gracias por usar Food Store. Hasta luego!");
                }
                default -> System.out.println("Opcion fuera de rango.");
            }
        }
 
        scanner.close();
    }
 
    //testeo de carga de datos iniciales
    private static void cargarDatosDePrueba(CategoriaService categoriaService,
                                            ProductoService productoService,
                                            UsuarioService usuarioService) throws NombreDuplicadoException, PrecioInvalidoException, StockInvalidoException, MailDuplicadoException {
        var bebidas = categoriaService.crearCategoria("Bebidas", "Gaseosas, jugos y aguas");
        var comidas = categoriaService.crearCategoria("Comidas", "Platos principales y snacks");
 
        productoService.crear("Coca Cola 500ml", 1500.0, "Gaseosa linea Coca", 50, "coca.png", true, bebidas);
        productoService.crear("Agua mineral 500ml", 800.0, "Agua sin gas", 100, "agua.png", true, bebidas);
        productoService.crear("Hamburguesa Clasica", 4500.0, "Completa con papas", 30, "burger.png", true, comidas);
        productoService.crear("Papas Fritas", 2200.0, "McCain Golden Long", 40, "papas.png", true, comidas);
 
        usuarioService.crearUsuario("Gonzalo", "Cano", "gonzalo@mail.com", "1122334455",
                "1234", enums.Rol.ADMIN);
        usuarioService.crearUsuario("Nicolas", "Silvan", "nico@mail.com", "1133445566",
                "1234", enums.Rol.USUARIO);

    }
}
