package ec.edu.ups.buscaminas.model;
//Aqui definimos el comportamiento de uan mina
public class CasillaMina extends Casilla {

    public CasillaMina() {
        super();
    }

    @Override
    public String obtenerSimbolo() {
        return "X"; 
    }
}