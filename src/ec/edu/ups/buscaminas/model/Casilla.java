package ec.edu.ups.buscaminas.model;

import java.io.Serializable; // 1. Importar la interfaz Serializable

// define que es una casilla 
public abstract class Casilla implements Serializable { // 2. Implementar la interfaz
    
    // 3. Añadir el serialVersionUID para evitar errores de compatibilidad
    private static final long serialVersionUID = 1L; 

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