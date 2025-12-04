package ec.edu.ups.util;

import java.io.*;
import ec.edu.ups.buscaminas.model.Tablero;

public class GestorPersistencia {

    private static final String RUTA_ARCHIVO = "partida_guardada.dat";

    /**
     * Guarda el tablero actual del juego en un archivo binario.
     */
    public static boolean guardarPartida(Tablero tablero) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA_ARCHIVO))) {
            oos.writeObject(tablero);
            return true;
        } catch (IOException e) {
            System.out.println("Error al guardar partida: " + e.getMessage());
            return false;
        }
    }

    /**
     * Carga una partida previamente guardada.
     */
    public static Tablero cargarPartida() {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(RUTA_ARCHIVO))) {
            return (Tablero) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar partida: " + e.getMessage());
            return null;
        }
    }

    /**
     * Verifica si existe una partida guardada previamente.
     */
    public static boolean existePartidaGuardada() {
        File archivo = new File(RUTA_ARCHIVO);
        return archivo.exists();
    }
}
