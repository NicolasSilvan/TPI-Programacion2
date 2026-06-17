
import entities.Categoria;
import entities.Pedido;
import entities.Producto;
import entities.Usuario;
import enums.Estado;
import enums.FormaPago;
import enums.Rol;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        //Testeo de toString() y creacion de objetos
        Categoria categoria = new Categoria((long)1, "", "");
        Producto producto = new Producto((long)1, "Alfajor", 2500.0, "Alfajor Guaymallen de dulce de leche",15, "123-ASD-456", true, categoria);
        Usuario usuario = new Usuario((long)1, "Manuel", "Adorni", "manuel_elcapo@yahoo.com", "11234567890", null, Rol.USUARIO);
        Pedido pedido = new Pedido(LocalDate.now(), Estado.PENDIENTE, FormaPago.TRANSFERENCIA, usuario);
        
        //Asociar productos a un pedido
        pedido.addDetallePedido(5, 2500.0, producto);
        
        System.out.println(categoria.toString());
        System.out.println(producto.toString());
        System.out.println(usuario.toString());
        System.out.println(pedido.toString());
    }
}
