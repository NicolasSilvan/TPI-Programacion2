package Menu;

import entities.Usuario;
import enums.Rol;
import exception.EntidadNoEncontradaException;
import exception.MailDuplicadoException;
import service.UsuarioService;

import java.util.List;

public class UsuarioMenu {

    private final UsuarioService usuarioService;
    private final LectorConsola lector;

    public UsuarioMenu(UsuarioService usuarioService, LectorConsola lector) {
        this.usuarioService = usuarioService;
        this.lector = lector;
    }

    public void mostrarMenu() throws MailDuplicadoException, EntidadNoEncontradaException {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- USUARIOS ---");
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
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios cargados.");
            return;
        }
        System.out.println("\n--- Listado de usuarios ---");
        for (Usuario u : usuarios) {
            System.out.println(u);
        }
    }

    private void crear() throws MailDuplicadoException {
        System.out.println("\n--- Crear usuario ---");
        String nombre = lector.leerTexto("Nombre: ");
        if (nombre.isBlank()) {
            System.out.println("Error: el nombre no puede estar vacio.");
            return;
        }
        String apellido = lector.leerTexto("Apellido: ");
        String mail = lector.leerTexto("Mail: ");
        if (mail.isBlank() || !mail.contains("@")) {
            System.out.println("Error: ingrese un mail valido.");
            return;
        }
        String celular = lector.leerTexto("Celular: ");
        String contrasena = lector.leerTexto("Contrasena: ");
        Rol rol = seleccionarRol();

        try {
            Usuario nuevo = usuarioService.crearUsuario(nombre, apellido, mail, celular, contrasena, rol);
            System.out.println("Usuario creado con exito. ID asignado: " + nuevo.getId());
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void editar() throws MailDuplicadoException, EntidadNoEncontradaException {
        listar();
        Long id = lector.leerLong("\nIngrese el ID del usuario a editar: ");
        try {
            usuarioService.buscarUsuarioPorId(id); // valida existencia antes de pedir datos

            String nuevoNombre = lector.leerTextoOpcional("Nuevo nombre (Enter para no modificar): ");
            String nuevoApellido = lector.leerTextoOpcional("Nuevo apellido (Enter para no modificar): ");
            String nuevoMail = lector.leerTextoOpcional("Nuevo mail (Enter para no modificar): ");
            String nuevoCelular = lector.leerTextoOpcional("Nuevo celular (Enter para no modificar): ");
            String nuevaContrasena = lector.leerTextoOpcional("Nueva contrasena (Enter para no modificar): ");

            boolean cambiarRol = lector.leerConfirmacion("Desea cambiar el rol?");
            Rol nuevoRol = null;
            if (cambiarRol) {
                nuevoRol = seleccionarRol();
            }

            usuarioService.editarUsuario(id, nuevoNombre, nuevoApellido, nuevoMail, nuevoCelular, nuevaContrasena, nuevoRol);
            System.out.println("Usuario actualizado con exito.");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void eliminar() throws EntidadNoEncontradaException {
        listar();
        Long id = lector.leerLong("\nIngrese el ID del usuario a eliminar: ");
        try {
            Usuario usuario = usuarioService.buscarUsuarioPorId(id);

            boolean confirmar = lector.leerConfirmacion("Confirma eliminar a '" + usuario.getNombre() + " " + usuario.getApellido() + "'?");
            if (!confirmar) {
                System.out.println("Operacion cancelada.");
                return;
            }

            usuarioService.eliminarUsuario(id);
            System.out.println("Usuario eliminado con exito.");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private Rol seleccionarRol() {
        System.out.println("Seleccione un rol:");
        Rol[] roles = Rol.values();
        for (int i = 0; i < roles.length; i++) {
            System.out.println((i + 1) + ". " + roles[i]);
        }
        int opcion = lector.leerEntero("Opcion: ");
        while (opcion < 1 || opcion > roles.length) {
            System.out.println("Opcion invalida.");
            opcion = lector.leerEntero("Opcion: ");
        }
        return roles[opcion - 1];
    }
}