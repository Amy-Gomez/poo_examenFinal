package ec.edu.ups.buscaminas.vista;

import ec.edu.ups.buscaminas.model.Tablero;
//Contrato (Interfaz) que define cómo el Controlador (Persona 2)
//se comunica con la capa de Presentación (Vista, Persona 3).
// Esto permite el aislamiento entre capas (MVC).
public interface IVista {
	void mostrarTablero(Tablero tablero);
    void mostrarMensaje(String mensaje);
    String solicitarJugada();   //SOLICITA: Accion (M/D) + Coordenada (ej: "D A5")
    
    //Pregunta al jugador si desea empezar una nueva partida (usado para el reinicio).
    //Devuelve true si desea jugar de nuevo, false si desea salir
    boolean confirmarNuevaPartida();
}
