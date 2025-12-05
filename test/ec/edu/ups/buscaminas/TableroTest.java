package ec.edu.ups.buscaminas;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import ec.edu.ups.buscaminas.model.Tablero;
import ec.edu.ups.buscaminas.model.Casilla;
import ec.edu.ups.buscaminas.model.CasillaMina;
import ec.edu.ups.buscaminas.model.CasillaVacia;

public class TableroTest {

    @Test
    void tableroDebeContenerDiezMinas() {
        Tablero tablero = new Tablero();

        int minas = 0;
        for(int f=0; f<10; f++){
            for(int c=0; c<10; c++){
                if(tablero.obtenerCasilla(f, c) instanceof CasillaMina)
                    minas++;
            }
        }

        assertEquals(10, minas, "El tablero debe contener exactamente 10 minas.");
    }


    @Test
    void casillasVaciasDebenTenerNumerosCorrectos() {
        Tablero tablero = new Tablero();

        for(int f=0; f<10; f++){
            for(int c=0; c<10; c++){
                Casilla casilla = tablero.obtenerCasilla(f,c);

                if(casilla instanceof CasillaVacia){
                    int valor = ((CasillaVacia) casilla).getMinasAlrededor();
                    assertTrue(valor >= 0 && valor <= 8,
                            "Valor inválido en casilla → No puede tener menos de 0 ni más de 8 minas alrededor.");
                }
            }
        }
    }


    @Test
    void obtenerCasillaFueraDeRangoDebeRetornarNull() {
        Tablero tablero = new Tablero();

        assertNull(tablero.obtenerCasilla(-1, 5));
        assertNull(tablero.obtenerCasilla(12, 4));
        assertNull(tablero.obtenerCasilla(3, -2));
        assertNull(tablero.obtenerCasilla(3, 11));
    }


    @Test
    void todasLasCasillasDebenEstarInicializadas() {
        Tablero tablero = new Tablero();

        for(int f=0; f<10; f++){
            for(int c=0; c<10; c++){
                assertNotNull(tablero.obtenerCasilla(f,c),
                        "Hay una casilla no inicializada en ("+f+","+c+")");
            }
        }
    }
}
