package ec.edu.ups.buscaminas.model;

// Aqui definimos el comportamiento de uan mina
public class CasillaMina extends Casilla {

    // Se añade el serialVersionUID, aunque hereda Serializable de Casilla
    private static final long serialVersionUID = 1L; 

    public CasillaMina() {
        super();
    }

    @Override
    public String obtenerSimbolo() {
        // Muestra "X" cuando se descubre una mina (Game Over)
        return "X"; 
    }
}