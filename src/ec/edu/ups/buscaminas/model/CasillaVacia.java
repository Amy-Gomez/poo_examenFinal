package ec.edu.ups.buscaminas.model;
//Aqui definimos las casillas seguras y la logica de los numeros.
public class CasillaVacia extends Casilla { 
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
        return (minasAlrededor == 0) ? " " : String.valueOf(minasAlrededor);
    }
}