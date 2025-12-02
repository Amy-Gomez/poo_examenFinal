package ec.edu.ups.buscaminas.controlador;

import ec.edu.ups.buscaminas.model.Tablero;
import ec.edu.ups.buscaminas.vista.IVista;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //  Implementación PROVISIONAL de la vista
        // Esta clase anónima implementa temporalmente IVista para probar el controlador
    	//La persona 3 debe reemplazar esto con su clase final: VistaConsola.java
        IVista vistaProvisional = new IVista() {
            Scanner sc = new Scanner(System.in);

            @Override
            public void mostrarTablero(Tablero tablero) {
                System.out.println("\n   0 1 2 3 4 5 6 7 8 9");
                System.out.println("  ---------------------");
                for (int i = 0; i < 10; i++) {
                    System.out.print((char)('A' + i) + " |"); // Imprime la coordenada de fila (A, B, C...)
                    for (int j = 0; j < 10; j++) {
                        ec.edu.ups.buscaminas.model.Casilla c = tablero.obtenerCasilla(i, j);
                        if (c.isDescubierta()) {
                            // Si está descubierta, muestra el símbolo
                            System.out.print(c.obtenerSimbolo() + " "); 
                        } else if (c.isMarcada()) {
                            // Usar 'F' para bandera
                            System.out.print("F "); 
                        } else {
                            // Si está cubierta, muestra el punto
                            System.out.print(". "); 
                        }
                    }
                    System.out.println();
                }
            }

            @Override
            public void mostrarMensaje(String mensaje) {
                System.out.println(">> " + mensaje);
            }

            @Override
            public String solicitarJugada() {
                System.out.print("Ingresa jugada (ej: D A5, M B7, o SALIR): ");
                // Se usa nextLine() para capturar toda la entrada y evitar errores de procesamiento
                return sc.nextLine().trim(); 
            }

           
             //Implementación del nuevo método para la funcionalidad de Reinicio.
             
            @Override
            public boolean confirmarNuevaPartida() {
                System.out.print(">> PARTIDA TERMINADA. ¿Deseas jugar una nueva partida? (S/N): ");
                String respuesta = sc.nextLine().trim();
                return respuesta.equalsIgnoreCase("S");
            }
        };

        ControladorJuego juego = new ControladorJuego(vistaProvisional);
        juego.iniciarJuego();
    }
}