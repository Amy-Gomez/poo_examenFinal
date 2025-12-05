package ec.edu.ups.buscaminas;

import static org.junit.jupiter.api.Assertions.*;
import ec.edu.ups.buscaminas.model.*;
import org.junit.jupiter.api.Test;

public class TableroTest {

    @Test
    void tableroDebeTener10x10() {
        Tablero t = new Tablero();
        assertEquals(10, t.getCasillas().length);
        assertEquals(10, t.getCasillas()[0].length);
    }

    @Test
    void tableroDebeContener10Minas() {
        Tablero t = new Tablero();
        int minas = 0;

        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                if(t.getCasillas()[i][j] instanceof CasillaMina)
                    minas++;
            }
        }
        assertEquals(10, minas);
    }

    @Test
    void contarMinasAlrededorFunciona() {
        Tablero t = new Tablero();
        Casilla[][] c = t.getCasillas();
        c[4][4] = new CasillaMina();

        assertEquals(1, t.contarMinasAlrededor(3,4));
        assertEquals(1, t.contarMinasAlrededor(5,5));
    }
}
