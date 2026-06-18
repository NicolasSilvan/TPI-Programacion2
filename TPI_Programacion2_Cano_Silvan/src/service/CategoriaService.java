package service;

import entities.Categoria;
import exception.EntidadNoEncontradaException;
import exception.NombreDuplicadoException;
 
import java.util.ArrayList;
import java.util.List;

public class CategoriaService {
    private final List<Categoria> categorias = new ArrayList<>();
    private Long contadorId = 1L;
    
    //retorna todas las categorias activas (getEliminado() = false)
    public List<Categoria> listarCategoria() {
        List<Categoria> activas = new ArrayList<>();
        for (Categoria c : categorias) {
            if (!c.getEliminado()) {
                activas.add(c);
            }
        }
        return activas;
    }
    
    //bucar categoria por ID
    public Categoria buscarCategoriaPorId(Long id) throws EntidadNoEncontradaException {
        for (Categoria c : categorias) {
            if (c.getId().equals(id) && !c.getEliminado()) {
                return c;
            }
        }
        throw new EntidadNoEncontradaException("No se encontro la categoria con ID: " + id);
    }
    
    //crea nueva categoria, valida que el nombre no sea duplicado
    public Categoria crearCategoria(String nombre, String descripcion) throws NombreDuplicadoException {
        validarNombreUnico(nombre, null);
 
        Categoria nueva = new Categoria(contadorId++, nombre, descripcion);
        categorias.add(nueva);
        return nueva;
    }
    
    //editar nombre/descripcion de una categoria existente
    public Categoria editar(Long id, String nuevoNombre, String nuevaDescripcion) throws EntidadNoEncontradaException, NombreDuplicadoException {
        Categoria categoria = buscarCategoriaPorId(id);
 
        if (nuevoNombre != null && !nuevoNombre.isBlank()) {
            validarNombreUnico(nuevoNombre, id);
            categoria.setNombre(nuevoNombre);
        }
        if (nuevaDescripcion != null && !nuevaDescripcion.isBlank()) {
            categoria.setDescripcion(nuevaDescripcion);
        }
        return categoria;
    }
    
    //marcar categoria como eliminada, excepcion si no existe o si esta eliminada
    public void eliminar(Long id) throws EntidadNoEncontradaException {
        Categoria categoria = buscarCategoriaPorId(id);
        categoria.setEliminado(true);
    }
    
    //validar que no exista categoria con el mismo nombre
    private void validarNombreUnico(String nombre, Long idExcluir) throws NombreDuplicadoException {
        for (Categoria c : categorias) {
            if (!c.getEliminado()
                    && c.getNombre().equalsIgnoreCase(nombre)
                    && !c.getId().equals(idExcluir)) {
                throw new NombreDuplicadoException("Ya existe una categoria con el nombre: '" + nombre + "'");
            }
        }
    }
}
