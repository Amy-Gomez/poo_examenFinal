package ec.edu.ups.buscaminas.model;

public class CasillaVacia extends Casilla {

    private static final long serialVersionUID = 1L;

    private int minasAlrededor;

    public CasillaVacia() {
        super();
        this.minasAlrededor = 0;
    }

    public int getMinasAlrededor() { return minasAlrededor; }
    public void setMinasAlrededor(int minasAlrededor) { this.minasAlrededor = minasAlrededor; }

    @Override
    public String obtenerSimbolo() {
        return minasAlrededor == 0 ? " " : String.valueOf(minasAlrededor);
    }
}
