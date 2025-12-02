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
    private final int FILAS= 10;
    private final int COLUMNAS= 10;
    
    public ControladorJuego(IVista vista) {
        this.vista = vista;
        this.juegoTerminado = false;
    }
    //Inicia el bucle principal del programa, que maneja múltiples partidas
    public void iniciarJuego() {
        vista.mostrarMensaje("=== BUSCAMINAS 1.0 ===");
        boolean continuarJugando= true;
        
     // Bucle extermo: Controla si el jugador quiere iniciar nuevas partidas
        
        while (continuarJugando) {
            
            this.tablero = new Tablero(); 
            this.juegoTerminado = false;
            vista.mostrarMensaje("¡Nueva Partida Iniciada! Use D A5 (Descubrir) o M B7 (Marcar/Bandera). O escriba SALIR si desea finalizar el juego.");

            // Bucle interno: Controla el flujo de una sola partida
            while (!juegoTerminado) {
                vista.mostrarTablero(tablero);
                String entrada = vista.solicitarJugada(); 

                // 2. Control de Salida
                if (entrada.equalsIgnoreCase("SALIR")) {
                    juegoTerminado = true;
                    continuarJugando = false; 
                    vista.mostrarMensaje("Juego cancelado. ¡Hasta pronto!");
                    break; 
                }

                procesarJugada(entrada);
                
                // 3. Verificación de Victoria/Derrota
                if (juegoTerminado) {
                    vista.mostrarTablero(tablero); 
                } else if (verificarVictoria()) {
                    vista.mostrarTablero(tablero);
                    vista.mostrarMensaje("¡FELICIDADES! HA GANADO. Todas las casillas seguras descubiertas :)");
                    juegoTerminado = true;
                }
            } 

            if (!continuarJugando) {
                break;
            }
            
            if (juegoTerminado) {
                continuarJugando = vista.confirmarNuevaPartida();
            }

        } 
    }

    
     //Convierte texto como "D A5" o "M B7" en acción y coordenadas, y ejecuta la lógica
     // entrada Texto ingresado por el usuario (ej: D A5)
    
    private void procesarJugada(String entrada) {
        String[] partes = entrada.trim().toUpperCase().split("\\s+"); // Separa por espacios

        if (partes.length < 2) {
            vista.mostrarMensaje("Formato inválido. Use: [ACCION] [COORDENADA] (ej: D A5).");
            return;
        }

        String accionStr = partes[0]; // "D" o "M"
        String coordStr = partes[1]; // "A5"

        if (coordStr.length() < 2) {
            vista.mostrarMensaje("Coordenada inválida.");
            return;
        }

        try {
            // 1. PARSEO DE COORDENADAS
            char letraFila = coordStr.charAt(0);
            int fila = letraFila - 'A'; 
            int columna = Integer.parseInt(coordStr.substring(1));

            // Validar rangos
            if (fila < 0 || fila >= FILAS || columna < 0 || columna >= COLUMNAS) {
                vista.mostrarMensaje("Coordenada fuera de rango.");
                return;
            }

            Casilla casilla = tablero.obtenerCasilla(fila, columna);
            if (casilla.isDescubierta()) {
                 vista.mostrarMensaje("Esa casilla ya está descubierta.");
                 return;
            }

            // 2. EJECUTAR ACCIÓN
            if (accionStr.equals("M")) { // Lógica de Marcar/Bandera
                
                // Si ya está marcada, la desmarca. Si no, la marca.
                boolean estaMarcada = casilla.isMarcada();
                casilla.setMarcada(!estaMarcada); 
                vista.mostrarMensaje(estaMarcada ? "Bandera removida." : "Bandera colocada.");

            } else if (accionStr.equals("D")) { // Lógica de Descubrir
                
                if (casilla.isMarcada()) {
                    vista.mostrarMensaje("¡Error! Debe quitar la bandera antes de intentar descubrir esta casilla.");
                    return;
                }
                revelarCasilla(fila, columna);
            } else {
                vista.mostrarMensaje("Acción no reconocida. Use 'D' para Descubrir o 'M' para Marcar.");
            }

        } catch (NumberFormatException e) {
            vista.mostrarMensaje("Formato de número inválido en la columna.");
        }
    }

    
     // ALGORITMO FLOOD FILL (Recursividad) para la revelación automática de casillas
     
    private void revelarCasilla(int f, int c) {
        Casilla casilla = tablero.obtenerCasilla(f, c);

        // Caso Base 1: Si es nulo, ya está descubierta, o tiene bandera (¡no debería llegar aquí!), finaliza.
        if (casilla == null || casilla.isDescubierta() || casilla.isMarcada()) {
            return;
        }

        // Revela la casilla actual
        casilla.setDescubierta(true);

        // Caso2: Es una MINA -> Game Over
        if (casilla instanceof CasillaMina) {
            juegoTerminado = true;
            vista.mostrarMensaje("¡BOOM! Ha pisado una mina. Game over :(");
            revelarTodoElTablero(); 
            return;
        }

        // Caso3: Es una casilla VACÍA
        if (casilla instanceof CasillaVacia) {
            CasillaVacia vacia = (CasillaVacia) casilla;

            // Solo si el número es 0 (no tiene minas alrededor), se abre alrededor (a los vecinos)
            if (vacia.getMinasAlrededor() == 0) {
                // Recorre los 8 vecinos (incluyendo diagonales)
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (i == 0 && j == 0) continue;
                        
                        int nuevaFila = f + i;
                        int nuevaCol = c + j;
                        
                        if (nuevaFila >= 0 && nuevaFila < FILAS && nuevaCol >= 0 && nuevaCol < COLUMNAS) {
                             revelarCasilla(nuevaFila, nuevaCol);
                        }
                    }
                }
            }
        }
    }

    
     //Verifica si el jugador ha ganado.
     
    private boolean verificarVictoria() {
        int totalCasillasSeguras = (FILAS * COLUMNAS) - 10; 
        int casillasSegurasDescubiertas = 0;

        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                Casilla c = tablero.obtenerCasilla(i, j);
                if (c instanceof CasillaVacia && c.isDescubierta()) {
                    casillasSegurasDescubiertas++;
                }
            }
        }
        return casillasSegurasDescubiertas == totalCasillasSeguras;
    }
    
    
     //Método auxiliar para mostrar todo el tablero (incluyendo minas) al perder
     
    private void revelarTodoElTablero() {
        for(int i=0; i<FILAS; i++) {
            for(int j=0; j<COLUMNAS; j++) {
                tablero.obtenerCasilla(i, j).setDescubierta(true);
            }
        }
    }
}