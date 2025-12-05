package ec.edu.ups.buscaminas;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ec.edu.ups.buscaminas.model.Tablero;
import ec.edu.ups.util.GestorPersistencia;

public class GestorPersistenciaTest {

    // Antes de cada prueba borramos archivo previo para evitar falsos positivos
    @BeforeEach
    void limpiarArchivo() {
        java.io.File f = new java.io.File("partida_guardada.dat");
        if(f.exists()) f.delete();
    }

    @Test
    void guardarYCargarPartidaFunciona() throws Exception { 
        Tablero original = new Tablero();

        // 1. Guardar
        GestorPersistencia.guardarJuego(original);

        // 2. Cargar
        Tablero cargado = GestorPersistencia.cargarJuego();

        // 3. Validar existencia del objeto cargado
        assertNotNull(cargado, "❌ El tablero cargado es NULL. No se guardó correctamente.");
    }

    @Test
    void debeDetectarArchivoGuardado() throws Exception { 
        assertFalse(GestorPersistencia.existePartidaGuardada(),
            "⚠ No debería haber archivo guardado antes de iniciar.");

        GestorPersistencia.guardarJuego(new Tablero());

        assertTrue(GestorPersistencia.existePartidaGuardada(),
            "❌ El archivo debería existir después de guardar partida.");
    }
}
