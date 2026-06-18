package service;

import entities.Usuario;
import enums.Rol;
import exception.EntidadNoEncontradaException;
import exception.MailDuplicadoException;
 
import java.util.ArrayList;
import java.util.List;

public class UsuarioService {
    private final List<Usuario> usuarios = new ArrayList<>();
    private Long contadorId = 1L;
    
    //listar usuarios no eliminados
    public List<Usuario> listarUsuarios() {
    List<Usuario> activos = new ArrayList<>();
    for (Usuario u : usuarios) {
        if (!u.getEliminado()) {
            activos.add(u);
        }
    }
        return activos;
    }   

    //buscar usuario por id
    public Usuario buscarUsuarioPorId(Long id) throws EntidadNoEncontradaException {
        for (Usuario u : usuarios) {
            if (u.getId().equals(id) && !u.getEliminado()) {
                return u;
            }
        }
        throw new EntidadNoEncontradaException("No se encontro el usuario con ID: " + id);
    }
    
    //Crear usuario
    public Usuario crearUsuario(String nombre, String apellido, String mail,
                         String celular, String contrasena, Rol rol) throws MailDuplicadoException {
        validarMailUnico(mail, null);
 
        Usuario nuevo = new Usuario(contadorId++, nombre, apellido, mail,
                                    celular, contrasena, rol);
        usuarios.add(nuevo);
        return nuevo;
    }
    
    //edito solo los campos nulos
    public Usuario editarUsuario(Long id, String nuevoNombre, String nuevoApellido,
                          String nuevoMail, String nuevoCelular,
                          String nuevaContrasena, Rol nuevoRol) throws MailDuplicadoException, EntidadNoEncontradaException {
        Usuario usuario = buscarUsuarioPorId(id);
 
        if (nuevoNombre != null && !nuevoNombre.isBlank()) {
            usuario.setNombre(nuevoNombre);
        }
        if (nuevoApellido != null && !nuevoApellido.isBlank()) {
            usuario.setApellido(nuevoApellido);
        }
        if (nuevoMail != null && !nuevoMail.isBlank()) {
            validarMailUnico(nuevoMail, id);
            usuario.setMail(nuevoMail);
        }
        if (nuevoCelular != null && !nuevoCelular.isBlank()) {
            usuario.setCelular(nuevoCelular);
        }
        if (nuevaContrasena != null && !nuevaContrasena.isBlank()) {
            usuario.setContrasena(nuevaContrasena);
        }
        if (nuevoRol != null) {
            usuario.setRol(nuevoRol);
        }
        return usuario;
    }
    
    //marcar usuario como eliminado
    public void eliminarUsuario(Long id) throws EntidadNoEncontradaException {
        Usuario usuario = buscarUsuarioPorId(id);
        usuario.setEliminado(true);
    }
    
    //validar que no exista otro usuario con el mismo mail
    private void validarMailUnico(String mail, Long idExcluir) throws MailDuplicadoException {
        for (Usuario u : usuarios) {
            if (!u.getEliminado()
                    && u.getMail().equalsIgnoreCase(mail)
                    && !u.getId().equals(idExcluir)) {
                throw new MailDuplicadoException("Ya existe un usuario con el mail: '" + mail + "'");
            }
        }
    }
}
