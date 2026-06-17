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
        this.setId(id);
        this.setEliminado(false);
        this.setCreatedAt(LocalDateTime.now());
    }
    
    public long getId(){ return this.id; }
    public void setId(long id) { this.id = id; }

    public boolean getEliminado() { return this.eliminado; }
    public void setEliminado(boolean eliminado) { this.eliminado = eliminado; }
    
    public LocalDateTime getCreatedAt() { return this.createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString(){
        return " - ID = " + this.id + "\n - Creado en : " + this.createdAt;
    }
    
}
