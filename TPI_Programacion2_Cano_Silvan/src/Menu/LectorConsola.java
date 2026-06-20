package Menu;

import java.util.Scanner;

//clase utilitarua para leer datos en consola con validacion y no repetir codigo en el main
public class LectorConsola {
    private final Scanner scanner;
    
    public LectorConsola(Scanner scanner) {
        this.scanner = scanner;
    }
    
    public String leerTexto(String mensaje) {
        System.out.println(mensaje);
        return scanner.nextLine().trim();
    }
    
    //lee texto pero permite dejarlo vacio (util para "enter para no modificar")
    public String leerTextoOpcional(String mensaje) {
        System.out.println(mensaje);
        String valor = scanner.nextLine().trim();
        return valor.isBlank() ? null : valor;
    }
    
    public int leerEntero(String mensaje){
        while(true) {
            System.out.println(mensaje);
            String entrada = scanner.nextLine().trim();
            try {
                return Integer.parseInt(entrada);
            } catch(NumberFormatException e) {
                System.out.println("Error, debe ingresar un numero entero valido...");
            }
        }
    }
    
    //misma logica que leerTextoOpcional, si el usuario deja vacio retorna null (no modificar)
    //retorna clase Integer para admitir valores nulos
    public Integer leerEnteroOpcional(String mensaje){
        while(true) {
            System.out.println(mensaje);
            String entrada = scanner.nextLine().trim();
            if (entrada.isBlank()) return null;
            try {
                return Integer.parseInt(entrada);
            } catch(NumberFormatException e) {
                System.out.println("Error, debe ingresar un numero entero valido, se mantiene el valor anterior...");
            }
        }
    }
    
    public Long leerLong(String mensaje) {
        while(true) {
            System.out.println(mensaje);
            String entrada = scanner.nextLine().trim();
            try {
                return Long.parseLong(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Error, debe ingresar un ID valido...");
            }
        }
    }
    
    public Double leerDouble(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim();
            try {
                return Double.parseDouble(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Error, debe ingresar un numero decimal valido...");
            }
        }
    }
    
    public Double leerDoubleOpcional(String mensaje) {
        System.out.print(mensaje);
        String entrada = scanner.nextLine().trim();
        if (entrada.isBlank()) return null;
        try {
            return Double.parseDouble(entrada);
        } catch (NumberFormatException e) {
            System.out.println("Error, debe ingresar un numero decimal valido, se mantiene el valor anterior...");
            return null;
        }
    }
    
    public boolean leerConfirmacion(String mensaje) {
        System.out.print(mensaje + " (S/N): ");
        String entrada = scanner.nextLine().trim();
        return entrada.equalsIgnoreCase("S");
    }
 
    public boolean leerBooleano(String mensaje) {
        System.out.print(mensaje + " (S/N): ");
        String entrada = scanner.nextLine().trim();
        return entrada.equalsIgnoreCase("S");
    }
}
