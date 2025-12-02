package ec.edu.ups.buscaminas.controlador;

import ec.edu.ups.buscaminas.model.Casilla;
import ec.edu.ups.buscaminas.model.CasillaMina;
import ec.edu.ups.buscaminas.model.CasillaVacia;
import ec.edu.ups.buscaminas.model.Tablero;
import ec.edu.ups.buscaminas.vista.IVista;

public class ControladorJuego {
	private Tablero tablero;
    private IVista vista;
    private boolean juegoTerminado;

    public ControladorJuego(IVista vista) {
        this.vista = vista;
        this.tablero = new Tablero(); // Se crea el tablero y se ponen las minas automáticamente
        this.juegoTerminado = false;
    }

    public void iniciarJuego() {
        vista.mostrarMensaje("=== BUSCAMINAS 1.0 ===");
        
        while (!juegoTerminado) {
            vista.mostrarTablero(tablero);
            String entrada = vista.solicitarCoordenada();

            // Validar si el usuario quiere salir
            if (entrada.equalsIgnoreCase("SALIR")) {
                juegoTerminado = true;
                vista.mostrarMensaje("Juego cancelado.");
                break;
            }

            procesarJugada(entrada);
            
            if (verificarVictoria()) {
                vista.mostrarTablero(tablero);
                vista.mostrarMensaje("¡FELICIDADES! HAS GANADO :)");
                juegoTerminado = true;
            }
        }
    }

  
     // Convierte texto como "A5" en coordenadas [0][5] y ejecuta la acción.
     // entrada Texto ingresado por el usuario
    
    private void procesarJugada(String entrada) {
        // Validación básica de longitud
        if (entrada == null || entrada.length() < 2) {
            vista.mostrarMensaje("Coordenada inválida. Use formato LetraNumero (ej: A5).");
            return;
        }

        try {
            // 1. PARSEO DE COORDENADAS
            // Convierte la letra a índice de fila (A=0, B=1, etc.)
            char letraFila = Character.toUpperCase(entrada.charAt(0));
            int fila = letraFila - 'A'; 

            // El resto del string es el número de columna
            int columna = Integer.parseInt(entrada.substring(1));

            // Validar rangos (0-9)
            if (fila < 0 || fila >= 10 || columna < 0 || columna >= 10) {
                vista.mostrarMensaje("Coordenada fuera de rango.");
                return;
            }

            // 2. Ejecutar accion
            revelarCasilla(fila, columna);

        } catch (NumberFormatException e) {
            vista.mostrarMensaje("Formato de número inválido.");
        }
    }

   
     // Lógica principal de recursividad (Flood Fill).
    
    private void revelarCasilla(int f, int c) {
        Casilla casilla = tablero.obtenerCasilla(f, c);

        // Caso Base 1: Si es nulo o ya está descubierta, no se hace nada
        if (casilla == null || casilla.isDescubierta()) {
            return;
        }

        // Revala la casilla actual
        casilla.setDescubierta(true);

        // Caso2: Es una MINA -> Game Over
        if (casilla instanceof CasillaMina) {
            juegoTerminado = true;
            vista.mostrarMensaje("¡BOOM! Has pisado una mina. Fin del juego :(");
            revelarTodoElTablero(); // Opcional: mostrar dónde estaban las minas
            return;
        }

        // Caso3: Es una casilla VACÍA
        if (casilla instanceof CasillaVacia) {
            CasillaVacia vacia = (CasillaVacia) casilla;

            // Algoritmo de expansión-Flood fill (Recursividad)
            // Solo si el número es 0 (no tiene minas alrededor), se muestra alrededpr
            if (vacia.getMinasAlrededor() == 0) {
                // Recorre los 8 vecinos
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        // Llamada recursiva a los vecinos
                        int nuevaFila = f + i;
                        int nuevaCol = c + j;
                        // Verifica límites antes de llamar para evitar errores (aunque obtenerCasilla maneja null)
                        if (nuevaFila >= 0 && nuevaFila < 10 && nuevaCol >= 0 && nuevaCol < 10) {
                             revelarCasilla(nuevaFila, nuevaCol);
                        }
                    }
                }
            }
        }
    }

    private boolean verificarVictoria() {
        int casillasSegurasDescubiertas = 0;
        int totalCasillasSeguras = (10 * 10) - 10; // Total - Minas

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Casilla c = tablero.obtenerCasilla(i, j);
                // Si es segura y está descubierta, cuenta
                if (c instanceof CasillaVacia && c.isDescubierta()) {
                    casillasSegurasDescubiertas++;
                }
            }
        }
        return casillasSegurasDescubiertas == totalCasillasSeguras;
    }
    
    // Método suplementario para mostrsr todo al perder
    private void revelarTodoElTablero() {
        for(int i=0; i<10; i++) {
            for(int j=0; j<10; j++) {
                tablero.obtenerCasilla(i, j).setDescubierta(true);
            }
        }
    }
}
