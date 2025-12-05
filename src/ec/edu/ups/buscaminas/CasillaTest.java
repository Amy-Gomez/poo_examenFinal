package ec.edu.ups.buscaminas;

import static org.junit.jupiter.api.Assertions.*;
import ec.edu.ups.buscaminas.model.*;
import org.junit.jupiter.api.Test;

public class CasillaTest {

    @Test
    void casillaSeMarcaYDesmarca() {
        Casilla c = new CasillaVacia();
        assertFalse(c.isMarcada());

        c.setMarcada(true);
        assertTrue(c.isMarcada());

        c.setMarcada(false);
        assertFalse(c.isMarcada());
    }

    @Test
    void casillaSeDescubre() {
        Casilla c = new CasillaVacia();
        assertFalse(c.isDescubierta());

        c.setDescubierta(true);
        assertTrue(c.isDescubierta());
    }
}
