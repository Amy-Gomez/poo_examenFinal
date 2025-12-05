package ec.edu.ups.buscaminas.util;

import java.io.*;
import ec.edu.ups.buscaminas.model.Tablero;

/**
 * Clase estática encargada de la persistencia (Guardar/Cargar) del objeto Tablero
 * mediante serialización de Java.
 * Esta clase requiere que todas las clases del Modelo sean Serializable para funcionar.
 */
public class GestorPersistencia {

    private static final String RUTA_ARCHIVO = "partida_guardada.dat";

    
     // Guarda el tablero actual del juego en un archivo binario.
     // tablero El objeto Tablero a guardar.
     // @throws Exception Si ocurre un error de I/O o serialización.
    
    public static void guardarJuego(Tablero tablero) throws Exception {
        // Renombrado de guardarPartida() a guardarJuego() para compatibilidad con ControladorJuego.java
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA_ARCHIVO))) {
            oos.writeObject(tablero);
        } catch (IOException e) {
            // Relanzamos la excepción para que el ControladorJuego la capture y muestre el error al usuario.
            throw new Exception("Error al guardar partida: " + e.getMessage(), e);
        }
    }

    
     // Carga una partida previamente guardada.
     // @return El objeto Tablero cargado o null si no existe una partida guardada.
     // @throws Exception Si ocurre un error de I/O o deserialización.
     
    public static Tablero cargarJuego() throws Exception {
        // Renombrado de cargarPartida() a cargarJuego() para compatibilidad con ControladorJuego.java
        File archivo = new File(RUTA_ARCHIVO);
        
        // Si el archivo no existe, no es un error, simplemente no hay partida guardada
        if (!archivo.exists() || archivo.length() == 0) {
            return null; 
        }
        
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(RUTA_ARCHIVO))) {
            return (Tablero) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Relanza la excepción para que el ControladorJuego la capture y muestre el error al usuario.
            throw new Exception("Error al cargar partida (archivo corrupto o clase no encontrada): " + e.getMessage(), e);
        }
    }

    
     // Verifica si existe una partida guardada previamente. 
    public static boolean existePartidaGuardada() {
        File archivo = new File(RUTA_ARCHIVO);
        return archivo.exists() && archivo.length() > 0;
    }
}