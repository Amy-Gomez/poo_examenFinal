package ec.edu.ups.buscaminas.vista;

import ec.edu.ups.buscaminas.model.Tablero;
//Contrato que debe cumplir la clase Vista (Persona 3), q me permite programar el controlador sin esperar a que la 
//vist esté terminada
public interface IVista {
	void mostrarTablero(Tablero tablero);
    void mostrarMensaje(String mensaje);
    String solicitarCoordenada(); // Solicita una coordenada (ej: "A5")
    //Pregunta al jugador si desea empezar una nueva partida (usado para el reinicio).
    //Devuelve true si desea jugar de nuevo, false si desea salir
    boolean confirmarNuevaPartida();
}
