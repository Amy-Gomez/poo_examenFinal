package ec.edu.ups.buscaminas.controlador;

import ec.edu.ups.buscaminas.model.Casilla;
import ec.edu.ups.buscaminas.model.CasillaMina;
import ec.edu.ups.buscaminas.model.CasillaVacia;
import ec.edu.ups.buscaminas.model.Tablero;
import ec.edu.ups.buscaminas.vista.IVista;

// CLASES INTEGRACIÓN 
import ec.edu.ups.buscaminas.util.JugadaInvalidaException; 
import ec.edu.ups.buscaminas.util.GestorPersistencia;     

public class ControladorJuego {

    private Tablero tablero;
    private IVista vista;
    private boolean juegoTerminado;
    private final int FILAS = 10;
    private final int COLUMNAS = 10;

    public ControladorJuego(IVista vista) {
        this.vista = vista;
        this.juegoTerminado = false;
    }

    
     //Inicia el bucle principal del programa, gestionando el menú de inicio y las partidas.
    
    public void iniciarJuego() {
        vista.mostrarMensaje("=== BUSCAMINAS 1.0 ===");
        
        boolean continuarJugando = true;

        // Bucle externo que maneja el menú y la posibilidad de múltiples partidas
        while (continuarJugando) {
            
            // Lógica de Menú (Inicio y Carga de Partida)
            if (tablero == null) {
                vista.mostrarMensaje("Menú Principal: 1. Nueva Partida / 2. Cargar Partida / 3. Salir");
                String opcion = vista.solicitarJugada().trim(); // Reutilizamos solicitarJugada

                if (opcion.equals("2")) {
                    try {
                        // INTEGRACIÓN: Cargar Partida
                        this.tablero = GestorPersistencia.cargarJuego();
                        if (this.tablero != null) {
                             vista.mostrarMensaje("Partida cargada exitosamente.");
                        } else {
                             vista.mostrarMensaje("No se encontró partida guardada. Iniciando nueva partida.");
                             this.tablero = new Tablero(); 
                        }
                    } catch (Exception e) {
                        vista.mostrarMensaje("Error al cargar partida: " + e.getMessage());
                        this.tablero = new Tablero(); // Inicia una nueva si falla
                    }
                } else if (opcion.equals("3") || opcion.equalsIgnoreCase("SALIR")) {
                    continuarJugando = false;
                    vista.mostrarMensaje("Juego cancelado. ¡Hasta pronto!");
                    break;
                } else { // Opción 1 o cualquier otra cosa inicia nueva partida
                    this.tablero = new Tablero(); 
                }
            }
            
            // Si salimos del menú sin salir del juego:
            if (!continuarJugando) break;

            this.juegoTerminado = false;
            vista.mostrarMensaje("¡Partida Iniciada! Usa D A5 (Descubrir) o M B7 (Marcar). Escribe GUARDAR o SALIR.");

            // BUCLE INTERNO: Controla el flujo de una sola partida
            while (!juegoTerminado) {
                vista.mostrarTablero(tablero);
                String entrada = vista.solicitarJugada();

                // Control de Comandos especiales
                if (entrada.equalsIgnoreCase("SALIR")) {
                    juegoTerminado = true;
                    continuarJugando = false; 
                    vista.mostrarMensaje("Juego cancelado. ¡Hasta pronto!");
                    break; 
                } else if (entrada.equalsIgnoreCase("GUARDAR")) {
                    // INTEGRACIÓN: Guardar Partida
                    try {
                        GestorPersistencia.guardarJuego(this.tablero);
                        vista.mostrarMensaje("Partida guardada exitosamente.");
                    } catch (Exception e) {
                        vista.mostrarMensaje("Error al guardar partida: " + e.getMessage());
                    }
                    continue; 
                }
                
                // INTEGRACIÓN: Manejo de Errores con Excepciones Personalizadas
                try {
                    procesarJugada(entrada);
                } catch (JugadaInvalidaException e) {
                    // Captura la excepción personalizada y muestra un mensaje 
                    vista.mostrarMensaje("ERROR: " + e.getMessage());
                } catch (Exception e) {
                    vista.mostrarMensaje("ERROR desconocido: " + e.getMessage());
                }
                
                // Verificación de Victoria/Derrota
                if (juegoTerminado) {
                    vista.mostrarTablero(tablero); 
                } else if (verificarVictoria()) {
                    vista.mostrarTablero(tablero);
                    vista.mostrarMensaje("¡FELICIDADES! HAS GANADO. Todas las casillas seguras descubiertas.");
                    juegoTerminado = true;
                }
            } 

            // Reinicio de partida
            if (continuarJugando && juegoTerminado) {
                if (vista.confirmarNuevaPartida()) {
                    tablero = null; // Vuelve al menú principal al reiniciar
                } else {
                    continuarJugando = false;
                }
            }

        } 
    }

   
     // Convierte texto como "D A5" o "M B7" en acción y coordenadas, y ejecuta la lógica.
     // entrada Texto ingresado por el usuario (ej: D A5)
     // @throws JugadaInvalidaException Si la entrada no tiene el formato correcto o está fuera de rango.
     
    private void procesarJugada(String entrada) throws JugadaInvalidaException {
        String[] partes = entrada.trim().toUpperCase().split("\\s+");

        if (partes.length < 2) {
            throw new JugadaInvalidaException("Formato inválido. Usa: [ACCION] [COORDENADA] (ej: D A5).");
        }

        String accionStr = partes[0]; 
        String coordStr = partes[1];

        if (coordStr.length() < 2) {
            throw new JugadaInvalidaException("Coordenada inválida: formato debe ser LetraNúmero (ej: A5).");
        }

        try {
            // 1. PARSEO DE COORDENADAS
            char letraFila = coordStr.charAt(0);
            int fila = letraFila - 'A'; 
            int columna = Integer.parseInt(coordStr.substring(1));

            // Validar rangos
            if (fila < 0 || fila >= FILAS || columna < 0 || columna >= COLUMNAS) {
                throw new JugadaInvalidaException("Coordenada fuera de rango del tablero (A0-J9).");
            }

            Casilla casilla = tablero.obtenerCasilla(fila, columna);
            
            // 2. EJECUTAR ACCIÓN
            if (accionStr.equals("M")) { 
                if (casilla.isDescubierta()) {
                    throw new JugadaInvalidaException("No se puede marcar una casilla ya descubierta.");
                }
                boolean estaMarcada = casilla.isMarcada();
                casilla.setMarcada(!estaMarcada); 
                vista.mostrarMensaje(estaMarcada ? "Bandera removida." : "Bandera colocada.");

            } else if (accionStr.equals("D")) { 
                
                if (casilla.isDescubierta()) {
                    throw new JugadaInvalidaException("Esa casilla ya está descubierta.");
                }

                if (casilla.isMarcada()) {
                    throw new JugadaInvalidaException("Debes quitar la bandera antes de intentar descubrir esta casilla.");
                }
                revelarCasilla(fila, columna);
            } else {
                throw new JugadaInvalidaException("Acción no reconocida. Usa 'D' (Descubrir) o 'M' (Marcar).");
            }

        } catch (NumberFormatException e) {
            throw new JugadaInvalidaException("El formato de la columna (número) es inválido.");
        }
    }

    private void revelarCasilla(int f, int c) {
        Casilla casilla = tablero.obtenerCasilla(f, c);
        if (casilla == null || casilla.isDescubierta() || casilla.isMarcada()) { return; }
        casilla.setDescubierta(true);
        if (casilla instanceof CasillaMina) {
            juegoTerminado = true;
            vista.mostrarMensaje("¡BOOM! Has pisado una mina. Fin del juego :(");
            revelarTodoElTablero(); 
            return;
        }
        if (casilla instanceof CasillaVacia) {
            CasillaVacia vacia = (CasillaVacia) casilla;
            if (vacia.getMinasAlrededor() == 0) {
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
    
    private void revelarTodoElTablero() {
        for(int i=0; i<FILAS; i++) {
            for(int j=0; j<COLUMNAS; j++) {
                tablero.obtenerCasilla(i, j).setDescubierta(true);
            }
        }
    }
}