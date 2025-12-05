package ec.edu.ups.buscaminas;

import static org.junit.jupiter.api.Assertions.*;
import ec.edu.ups.buscaminas.model.Tablero;
import ec.edu.ups.util.GestorPersistencia;
import org.junit.jupiter.api.Test;

public class GestorPersistenciaTest {

    @Test
    void guardarYCargarPartidaFunciona() {
        Tablero original = new Tablero();
        assertTrue(GestorPersistencia.guardarPartida(original));

        Tablero cargado = GestorPersistencia.cargarPartida();
        assertNotNull(cargado);
    }

    @Test
    void debeDetectarArchivoGuardado() {
        new Tablero();
        GestorPersistencia.guardarPartida(new Tablero());
        assertTrue(GestorPersistencia.existePartidaGuardada());
    }
}
