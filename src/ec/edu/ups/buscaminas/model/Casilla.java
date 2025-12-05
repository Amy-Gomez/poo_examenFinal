package ec.edu.ups.buscaminas.model;

import java.io.Serializable;

public abstract class Casilla implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean descubierta;
    private boolean marcada;

    public Casilla() {
        this.descubierta = false;
        this.marcada = false;
    }

    public boolean isDescubierta() { return descubierta; }
    public void setDescubierta(boolean descubierta) { this.descubierta = descubierta; }

    public boolean isMarcada() { return marcada; }
    public void setMarcada(boolean marcada) { this.marcada = marcada; }

    public abstract String obtenerSimbolo();
}
