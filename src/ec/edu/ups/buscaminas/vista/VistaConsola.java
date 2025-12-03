package CE.educación.Unión Postal Universal.buscaminas.vista;

import java.util.Scanner;

import CE.educación.Unión Postal Universal.buscaminas.modelo.Casilla;
import CE.educación.Unión Postal Universal.buscaminas.modelo.Tablero;

public class VistaConsola implements IVista {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void mostrarTablero(Tablero tablero) {
        Casilla[][] casillas = tablero.getCasillas();

        // Encabezado de columnas
        System.out.print("   ");
        for (int col = 1; col <= 10; col++) {
            System.out.printf("%2d ", col);
        }
        System.out.println();

        // Filas A–H
        for (int fila = 0; fila < casillas.length; fila++) {
            char letraFila = (char) ('A' + fila);
            System.out.print(" " + letraFila + " ");

            for (int col = 0; col < casillas[fila].length; col++) {
                Casilla c = casillas[fila][col];
                System.out.printf("%2s ", obtenerSimboloCasilla(c));
            }
            System.out.println();
        }
        System.out.println();
    }

    private String obtenerSimboloCasilla(Casilla c) {

        // Descubierta
        if (c.isDescubierta()) {

            if (c.esMina()) {
                return "X"; // mina descubierta por explotar
            }

            int minas = c.getMinasAlrededor();
            return minas == 0 ? " " : String.valueOf(minas);
        }

        // Marcada (bandera)
        if (c.isMarcada()) {
            return "F";
        }

        // Oculta
        return "#";
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    /**
     * Solicita jugada en formato:
     *  - "D A5" descubrir A5
     *  - "M C10" marcar C10
     */
    @Override
    public String SolicitarJugada() {
        while (true) {

            System.out.print("Ingrese jugada (ej: D A5 o M C10): ");
            String linea = scanner.nextLine().trim().toUpperCase();

            // Validar formato: ACCIÓN + ESPACIO + COORDENADA
            if (linea.matches("^[DM] [A-H](10|[1-9])$")) {
                return linea;
            }

            System.out.println("⚠ Formato inválido. Ejemplos válidos: D A5   M C10");
        }
    }

    @Override
    public boolean confirmarNuevaPartida() {
        while (true) {
            System.out.print("¿Deseas jugar de nuevo? (S/N): ");
            String r = scanner.nextLine().trim().toUpperCase();

            if (r.equals("S")) return true;
            if (r.equals("N")) return false;

            System.out.println("⚠ Respuesta inválida. Use S o N.");
        }
    }
}
