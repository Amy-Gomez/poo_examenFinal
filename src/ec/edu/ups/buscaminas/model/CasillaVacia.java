package ec.edu.ups.buscaminas.model;

//Se define las casillas seguras y la logica de los numeros.
public class CasillaVacia extends Casilla {
    
    // Se añade el serialVersionUID, aunque hereda Serializable de Casilla
    private static final long serialVersionUID = 1L; 

    private int minasAlrededor;

    public CasillaVacia() {
        super(); 
        this.minasAlrededor = 0;
    }

    public int getMinasAlrededor() {
        return minasAlrededor;
    }

    public void setMinasAlrededor(int minasAlrededor) {
        this.minasAlrededor = minasAlrededor;
    }

    @Override
    public String obtenerSimbolo() {
        // Si no hay minas alrededor, muestra un espacio. De lo contrario, muestra el número.
        return (minasAlrededor == 0) ? " " : String.valueOf(minasAlrededor);
    }
}