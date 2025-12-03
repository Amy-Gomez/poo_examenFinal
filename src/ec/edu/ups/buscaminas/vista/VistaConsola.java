package ec.edu.ups.buscaminas.vista;

import java.util.Scanner;
import ec.edu.ups.buscaminas.model.Casilla;
import ec.edu.ups.buscaminas.model.Tablero;

public class VistaConsola implements IVista {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void mostrarBienvenida() {
        System.out.println("=== BUSCAMINAS UPS ===");
        System.out.println("Formato de coordenada: A1..H10");
        System.out.println("Acciones: D = descubrir, M = marcar/desmarcar");
        System.out.println();
    }

    @Override
    public char pedirAccion() {
        while (true) {
            System.out.print("Acción (D/M): ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.equals("D") || input.equals("M")) {
                return input.charAt(0);
            }

            System.out.println("⚠ Acción inválida. Use D o M.");
        }
    }

    @Override
    public String pedirCoordenada() {
        while (true) {
            System.out.print("Ingrese coordenada (ej: A5, C10): ");
            String coord = scanner.nextLine().trim().toUpperCase();

            if (coord.matches("^[A-H](10|[1-9])$")) {
                return coord;
            }

            System.out.println("⚠ Coordenada inválida. Use A1..H10.");
        }
    }

    @Override
    public void mostrarTablero(Tablero tablero, boolean mostrarMinas) {
        Casilla[][] casillas = tablero.getCasillas();

        // Encabezado columnas
        System.out.print("   ");
        for (int col = 1; col <= 10; col++) {
            System.out.printf("%2d ", col);
        }
        System.out.println();

        for (int fila = 0; fila < casillas.length; fila++) {
            char letraFila = (char) ('A' + fila);
            System.out.print(" " + letraFila + " ");

            for (int col = 0; col < casillas[fila].length; col++) {
                Casilla c = casillas[fila][col];
                String simbolo = obtenerSimboloCasilla(c, mostrarMinas);
                System.out.printf("%2s ", simbolo);
            }
            System.out.println();
        }
        System.out.println();
    }

    private String obtenerSimboloCasilla(Casilla c, boolean mostrarMinas) {

        if (c.isDescubierta()) {
            if (c.esMina()) {
                return mostrarMinas ? "X" : ".";
            } else {
                int minas = c.getMinasAlrededor();
                return minas == 0 ? " " : String.valueOf(minas);
            }
        }

        if (c.isMarcada()) return "F";

        if (mostrarMinas && c.esMina()) return "X";

        return "#";
    }

    @Override
    public void mostrarVictoria() {
        System.out.println("🎉 ¡Felicidades, ganaste! 🎉");
    }

    @Override
    public void mostrarDerrota() {
        System.out.println("💣 BOOM... Perdiste. 💣");
    }

    @Override
    public void mostrarError(String mensaje) {
        System.out.println("⚠ ERROR: " + mensaje);
    }
}
