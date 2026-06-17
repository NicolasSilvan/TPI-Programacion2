package entities;

import enums.Rol;
import java.util.ArrayList;
import java.util.List;
 
public class Usuario extends Base {
 
    private String nombre;
    private String apellido;
    private String mail;
    private String celular;
    private String contrasena;
    private Rol rol;
 
    // Relacion 1:N con Pedido (un usuario tiene muchos pedidos)
    private List<Pedido> pedidos;
 
    public Usuario() {
        super();
        this.pedidos = new ArrayList<>();
    }
 
    public Usuario(Long id, String nombre, String apellido, String mail,
                   String celular, String contrasena, Rol rol) {
        super(id);
        this.setNombre(nombre);
        this.setApellido(apellido);
        this.setMail(mail);
        this.setCelular(celular);
        this.setContrasena(contrasena);
        this.setRol(rol);
        this.pedidos = new ArrayList<>();
    }
    
    public String getNombre() { return this.nombre; }
    public void setNombre(String nombre) {
        if(nombre != null && !nombre.isEmpty()){
            this.nombre = nombre;
        } else {
            System.out.println("El nombre no puede ser vacio para el usuario, seteando nombre generico...");
            this.nombre = "Usuario_" + this.getId();
        }
    }

    public String getApellido() { return this.apellido; }
    public void setApellido(String apellido) {
        if(apellido != null && !apellido.isEmpty()) {
            this.apellido = apellido;
        } else {
            System.out.println("El apellido del Usuario no puede ser vacio, seteando apellido generico...");
            this.apellido = "Apellido_" + this.getId();
        }
    }

    public String getMail() { return this.mail; }
    public void setMail(String mail) { 
        if(mail != null && !mail.isEmpty()) {
            this.mail = mail;
        } else {
            this.mail = "-------";
        }
    }

    public String getCelular() { return this.celular; }
    public void setCelular(String celular) {
        if(celular != null && !celular.isEmpty()) {
            this.celular = celular;
        } else {
            this.celular = "-------";
        }
    }

    public String getContrasena() { return this.contrasena; }
    public void setContrasena(String contrasena) {
        if(contrasena != null && !contrasena.isEmpty()) {
            this.contrasena = contrasena;
        } else {
            System.out.println("La contrasena no puede ser vacia, seteando contrasena a [1234]...");
            this.contrasena = "1234";
        }
    }

    public Rol getRol() { return this.rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    public List<Pedido> getPedidos() { return this.pedidos; }
    public void setPedidos(List<Pedido> pedidos) { this.pedidos = pedidos; }
    
    @Override
    public String toString() {
        return "Usuario {\n - ID : " + this.getId() + "\n - Nombre : " + this.nombre + " " + this.apellido + 
                "\n - Mail : " + this.mail + "\n - Celular : " + this.celular + "\n - Rol : " + this.rol + "\n}";
    }
}
