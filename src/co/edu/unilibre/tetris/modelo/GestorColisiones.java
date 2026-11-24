package co.edu.unilibre.tetris.modelo;

public class GestorColisiones {
    private int anchoTablero;
    private int altoTablero;

    // Constructor vacío por si se usa sin parámetros
    public GestorColisiones() {
        this.anchoTablero = 10;
        this.altoTablero = 20;
    }

    // Constructor que recibe las dimensiones (exigido por la prueba)
    public GestorColisiones(int anchoTablero, int altoTablero) {
        this.anchoTablero = anchoTablero;
        this.altoTablero = altoTablero;
    }

    public boolean verificarColisionParedes(PiezaElemental pieza, TableroJuego tablero, int nuevoX, int nuevoY) {
        int[][] matrizForma = pieza.getFormaMatriz();
        int filasForma = matrizForma.length;
        int columnasForma = matrizForma[0].length;

        for (int i = 0; i < filasForma; i++) {
            for (int j = 0; j < columnasForma; j++) {
                if (matrizForma[i][j] != 0) {
                    int posXTablero = nuevoX + j;
                    int posYTablero = nuevoY + i;

                    if (posXTablero < 0 || posXTablero >= tablero.getAncho() || posYTablero >= tablero.getAlto()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Método añadido para que coincida exactamente con la prueba GestorColisionesTest
    public boolean verificarColisionExterna(int x, int y) {
        return x < 0 || x >= anchoTablero || y < 0 || y >= altoTablero;
    }
}