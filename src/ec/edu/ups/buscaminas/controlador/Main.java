package ec.edu.ups.buscaminas.controlador;

import ec.edu.ups.buscaminas.vista.IVista;
import ec.edu.ups.buscaminas.vista.VistaConsola;

public class Main {

    public static void main(String[] args) {

        // Crear la vista definitiva en consola (Persona 3)
        IVista vista = new VistaConsola();

        // Pasar la vista al controlador del juego
        ControladorJuego juego = new ControladorJuego(vista);

        // Iniciar el ciclo principal del juego
        juego.iniciarJuego();
    }
}
