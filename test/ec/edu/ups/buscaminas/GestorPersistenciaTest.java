package ec.edu.ups.buscaminas;

import static org.junit.jupiter.api.Assertions.*;
import ec.edu.ups.buscaminas.model.Tablero;
import ec.edu.ups.buscaminas.util.GestorPersistencia;

import org.junit.jupiter.api.Test;

public class GestorPersistenciaTest {

    @Test
    void guardarYCargarPartidaFunciona() throws Exception { 
        Tablero original = new Tablero();
        // CORRECCIÓN 1: Llamar a guardarJuego() en lugar de guardarPartida()
        GestorPersistencia.guardarJuego(original); 

        // CORRECCIÓN 2: Llamar a cargarJuego() en lugar de cargarPartida()
        Tablero cargado = GestorPersistencia.cargarJuego();
        assertNotNull(cargado);
    }

    @Test
    void debeDetectarArchivoGuardado() throws Exception { 
        // Aunque no usemos el objeto, lo inicializamos para asegurar la creación del archivo
        new Tablero(); 
        
        // CORRECCIÓN 3: Llamar a guardarJuego()
        GestorPersistencia.guardarJuego(new Tablero());
        assertTrue(GestorPersistencia.existePartidaGuardada());
    }
}