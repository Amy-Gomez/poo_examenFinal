package ec.edu.ups.buscaminas.controlador;
import ec.edu.ups.buscaminas.model.Tablero;
import ec.edu.ups.buscaminas.vista.IVista;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
        // Implementación rápida e incompleta de la vista (para probar controlador), temporaal
        IVista vistaProvisional = new IVista() {
            Scanner sc = new Scanner(System.in);

            @Override
            public void mostrarTablero(Tablero tablero) {
                System.out.println("   0 1 2 3 4 5 6 7 8 9");
                System.out.println("  ---------------------");
                for (int i = 0; i < 10; i++) {
                    System.out.print((char)('A' + i) + " |"); // Imprime A, B, C...
                    for (int j = 0; j < 10; j++) {
                        ec.edu.ups.buscaminas.model.Casilla c = tablero.obtenerCasilla(i, j);
                        if (c.isDescubierta()) {
                            System.out.print(c.obtenerSimbolo() + " ");
                        } else {
                            System.out.print(". "); // Cubierto
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
            public String solicitarCoordenada() {
                System.out.print("Ingresa coordenada (ej: A5): ");
                return sc.next();
            }

            @Override
            public boolean confirmarSalir() {
                return false;
            }
        };

        ControladorJuego juego = new ControladorJuego(vistaProvisional);
        juego.iniciarJuego();
    }
}
