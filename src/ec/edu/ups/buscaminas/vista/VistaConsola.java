package ec.edu.ups.buscaminas.vista;

import java.util.Scanner;

import ec.edu.ups.buscaminas.model.Casilla;
import ec.edu.ups.buscaminas.model.CasillaMina;
import ec.edu.ups.buscaminas.model.CasillaVacia;
import ec.edu.ups.buscaminas.model.Tablero;

public class VistaConsola implements IVista {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void mostrarTablero(Tablero tablero) {
        Casilla[][] casillas = tablero.getCasillas();

        // Encabezado columnas 0–9 (como la vista provisional)
        System.out.print("   ");
        for (int col = 0; col < 10; col++) {
            System.out.printf("%2d ", col);
        }
        System.out.println();

        // Filas A–J
        for (int fila = 0; fila < casillas.length; fila++) {
            char letra = (char) ('A' + fila);
            System.out.print(" " + letra + " ");

            for (int col = 0; col < casillas[fila].length; col++) {
                Casilla c = casillas[fila][col];
                System.out.printf("%2s ", obtenerSimbolo(c));
            }
            System.out.println();
        }
        System.out.println();
    }

    // Decide cómo dibujar cada casilla
    private String obtenerSimbolo(Casilla c) {

        if (c.isDescubierta()) {
            if (c instanceof CasillaMina) {
                return "X";
            }
            if (c instanceof CasillaVacia) {
                int minas = ((CasillaVacia) c).getMinasAlrededor();
                return minas == 0 ? " " : String.valueOf(minas);
            }
        }

        if (c.isMarcada()) {
            return "F";
        }

        return "#"; // oculta
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    @Override
    public String solicitarJugada() {

        while (true) {
            System.out.print("Ingrese jugada (ej: D A5, M B7 o SALIR): ");
            String input = scanner.nextLine().trim().toUpperCase();

            // Validación columnas 0–9 ///////// IMPORTANTE ////////////
            if (input.matches("^[DM] [A-J][0-9]$")) {
                return input;
            }

            if (input.equalsIgnoreCase("SALIR")) {
                return "SALIR";
            }

            System.out.println("⚠ Entrada inválida. Ejemplos válidos: D A0, D A5, M C9");
        }
    }

    @Override
    public boolean confirmarNuevaPartida() {
        while (true) {
            System.out.print("¿Deseas jugar nuevamente? (S/N): ");
            String r = scanner.nextLine().trim().toUpperCase();

            if (r.equals("S")) return true;
            if (r.equals("N")) return false;

            System.out.println("⚠ Solo S o N.");
        }
    }
}
