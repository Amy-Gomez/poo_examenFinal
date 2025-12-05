package ec.edu.ups.util;

import java.io.*;
import ec.edu.ups.buscaminas.model.Tablero;

public class GestorPersistencia {

    private static final String RUTA_ARCHIVO = "partida_guardada.dat";

    // ⇩⇩ COINCIDE CON EL TEST ⇩⇩
    public static void guardarJuego(Tablero tablero) throws Exception {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA_ARCHIVO))) {
            oos.writeObject(tablero);
        } catch (IOException e) {
            throw new Exception("❌ Error al guardar partida: " + e.getMessage(), e);
        }
    }

    // ⇩⇩ COINCIDE CON EL TEST ⇩⇩
    public static Tablero cargarJuego() throws Exception {
        File archivo = new File(RUTA_ARCHIVO);

        if (!archivo.exists() || archivo.length() == 0)
            return null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (Tablero) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new Exception("❌ Error al cargar partida: " + e.getMessage(), e);
        }
    }

    // Igual que tu test requiere
    public static boolean existePartidaGuardada() {
        File archivo = new File(RUTA_ARCHIVO);
        return archivo.exists() && archivo.length() > 0;
    }
}
