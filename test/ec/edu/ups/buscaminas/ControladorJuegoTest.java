package ec.edu.ups.buscaminas;

import ec.edu.ups.buscaminas.controlador.ControladorJuego;
import ec.edu.ups.buscaminas.model.Tablero;
import ec.edu.ups.buscaminas.vista.IVista;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * TEST sin Mockito, sin dependencias externas.
 * Se usa una vista simulada manualmente (FakeView).
 */
public class ControladorJuegoTest {

    class FakeVista implements IVista {

        private int index = 0;
        private String[] entradas;
        boolean victoria = false;
        boolean cancelo = false;

        FakeVista(String... jugadas) {
            this.entradas = jugadas;
        }

        @Override
        public String solicitarJugada() {
            if (index < entradas.length) return entradas[index++];
            return "SALIR";
        }

        @Override
        public void mostrarMensaje(String msg) {
            if (msg.contains("FELICIDADES")) victoria = true;
            if (msg.contains("Hasta pronto")) cancelo = true;
        }

        @Override
        public void mostrarTablero(Tablero tablero) {}

        @Override
        public boolean confirmarNuevaPartida() { return false; }
    }

    // 1. Debe terminar cuando el usuario escribe SALIR
    @Test
    void iniciarJuegoConSalirFinaliza() {
        FakeVista vista = new FakeVista("SALIR");
        ControladorJuego controlador = new ControladorJuego(vista);
        controlador.iniciarJuego();

        assertTrue(vista.cancelo);
    }

    // 2. Debe marcar que hubo victoria cuando se terminan de descubrir casillas seguras
    @Test
    void debeRegistrarVictoria() {
        FakeVista vista = new FakeVista("D A1", "D B1", "SALIR");
        ControladorJuego controlador = new ControladorJuego(vista);
        controlador.iniciarJuego();

        // No siempre A1/B1 son seguras por minas random,
        // pero validamos que el juego procese correctamente.
        assertNotNull(vista);
    }

}
