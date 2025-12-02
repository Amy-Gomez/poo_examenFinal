package ec.edu.ups.buscaminas.model;

import java.util.Random; 

public class Tablero {
    // Atributos
    private Casilla[][] casillas;
    private final int FILAS = 10;
    private final int COLUMNAS = 10;
    private final int NUM_MINAS = 10;

    // Constructor
    public Tablero() {
        this.casillas = new Casilla[FILAS][COLUMNAS];
        inicializarTablero();
        colocarMinas();
        calcularNumeros();
    }

    
    private void inicializarTablero() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                casillas[i][j] = new CasillaVacia();
            }
        }
    }

    private void colocarMinas() {
        Random random = new Random();
        int minasColocadas = 0;

        while (minasColocadas < NUM_MINAS) {
            int f = random.nextInt(FILAS);
            int c = random.nextInt(COLUMNAS);

           
            if (!(casillas[f][c] instanceof CasillaMina)) {
                casillas[f][c] = new CasillaMina();
                minasColocadas++;
            }
        }
    }

   
    private void calcularNumeros() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
        
                if (casillas[i][j] instanceof CasillaVacia) {
                    int minasCercanas = contarMinasAlrededor(i, j);
                    
                  
                    CasillaVacia casillaVacia = (CasillaVacia) casillas[i][j];
                    casillaVacia.setMinasAlrededor(minasCercanas);
                }
            }
        }
    }

    
    public int contarMinasAlrededor(int fila, int col) {
        int contador = 0;

        
        for (int i = -1; i <= 1; i++) {
           
            for (int j = -1; j <= 1; j++) {
                
                
                if (i == 0 && j == 0) {
                    continue; 
                }

                int nuevaFila = fila + i;
                int nuevaCol = col + j;

               
                if (nuevaFila >= 0 && nuevaFila < FILAS && 
                    nuevaCol >= 0 && nuevaCol < COLUMNAS) {
                    
                  
                    if (casillas[nuevaFila][nuevaCol] instanceof CasillaMina) {
                        contador++;
                    }
                }
            }
        }
        return contador;
    }

   
    public Casilla obtenerCasilla(int fila, int col) {
        if (fila >= 0 && fila < FILAS && col >= 0 && col < COLUMNAS) {
            return casillas[fila][col];
        }
        return null; 
    }

    public Casilla[][] getCasillas() {
        return casillas;
    }
} 
