package ec.edu.ups.buscaminas.controlador;

import ec.edu.ups.buscaminas.vista.VistaConsola; 
import ec.edu.ups.buscaminas.vista.IVista;

public class Main {
    public static void main(String[] args) {
        
        IVista vistaFinal = new VistaConsola(); 

        ControladorJuego juego = new ControladorJuego(vistaFinal);
        juego.iniciarJuego();
    }
}