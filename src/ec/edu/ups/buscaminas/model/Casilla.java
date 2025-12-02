package ec.edu.ups.buscaminas.model;
// Aqui definimos que es una casilla 
public abstract class Casilla { 
    private boolean descubierta;
    private boolean marcada;

    public Casilla() {
        this.descubierta = false;
        this.marcada = false;
    }

    public boolean isDescubierta() {
        return descubierta;
    }

    public void setDescubierta(boolean descubierta) {
        this.descubierta = descubierta;
    }

    public boolean isMarcada() {
        return marcada;
    }

    public void setMarcada(boolean marcada) {
        this.marcada = marcada;
    }
    
    public abstract String obtenerSimbolo();
}