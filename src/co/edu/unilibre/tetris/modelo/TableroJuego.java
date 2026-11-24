package co.edu.unilibre.tetris.modelo;

public class TableroJuego {
    private int alto;
    private int ancho;
    private int[][] matrizTablero;

    public TableroJuego(int alto, int ancho) {
        this.alto = alto;
        this.ancho = ancho;
        this.matrizTablero = new int[alto][ancho];
    }

    public int getAlto() {
        return alto;
    }

    public int getAncho() {
        return ancho;
    }

    public int[][] getMatrizTablero() {
        return matrizTablero;
    }

    // Método solicitado por la prueba para mover/verificar posiciones en el tablero
    public boolean moverPieza(int x, int y) {
        // Valida si la coordenada está dentro de los límites del tablero
        return x >= 0 && x < ancho && y >= 0 && y < alto;
    }

    // Método para simular el llenado de una fila en las pruebas
    public void llenarFilaPrueba(int fila) {
        if (fila >= 0 && fila < alto) {
            for (int j = 0; j < ancho; j++) {
                matrizTablero[fila][j] = 1;
            }
        }
    }

    // Ajustado para retornar la cantidad de líneas eliminadas y cumplir con el test
    public int procesarLineasCompletas() {
        int lineasEliminadas = 0;
        for (int i = alto - 1; i >= 0; i--) {
            boolean lineaLlena = true;
            for (int j = 0; j < ancho; j++) {
                if (matrizTablero[i][j] == 0) {
                    lineaLlena = false;
                    break;
                }
            }
            if (lineaLlena) {
                lineasEliminadas++;
                // Bajar las líneas superiores
                for (int k = i; k > 0; k--) {
                    matrizTablero[k] = matrizTablero[k - 1].clone();
                }
                matrizTablero[0] = new int[ancho];
                i++; // Volver a revisar la misma fila
            }
        }
        return lineasEliminadas;
    }
}