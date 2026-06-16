package entities;

import java.time.LocalDateTime;

public abstract class Base {
   
    private Long id;
    private boolean eliminado;
    private LocalDateTime createdAt;
 
    public Base() {
        this.eliminado = false;
        this.createdAt = LocalDateTime.now();
    }
 
    public Base(Long id) {
        this.id = id;
        this.eliminado = false;
        this.createdAt = LocalDateTime.now();
    } 
}
