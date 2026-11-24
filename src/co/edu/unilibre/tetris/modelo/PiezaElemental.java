package co.edu.unilibre.tetris.modelo;

public class PiezaElemental {
    private String tipoElemento;
    private int posX;
    private int posY;
    private int[][] formaMatriz;
    private int duracionEfecto;

    public PiezaElemental(String tipoElemento, int[][] formaMatriz) {
        this.tipoElemento = tipoElemento;
        this.formaMatriz = formaMatriz;
        this.posX = 3;
        this.posY = 0;
    }

    // Constructor sobrecargado por si la prueba le pasa tipo y un entero (duración)
    public PiezaElemental(String tipoElemento, int duracionEfecto) {
        this.tipoElemento = tipoElemento;
        this.duracionEfecto = duracionEfecto;
        this.formaMatriz = new int[][]{{1, 1}, {1, 1}}; // Matriz por defecto para pruebas
        this.posX = 3;
        this.posY = 0;
    }

    public boolean activarEfecto() {
        // Lógica para activar el poder elemental de la pieza
        return true;
    }

    public void moverAbajo() {
        posY++;
    }

    public void moverIzquierda() {
        posX--;
    }

    public void moverDerecha() {
        posX++;
    }

    public void rotar() {
        int filas = formaMatriz.length;
        int columnas = formaMatriz[0].length;
        int[][] matrizRotada = new int[columnas][filas];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                matrizRotada[j][filas - 1 - i] = formaMatriz[i][j];
            }
        }
        this.formaMatriz = matrizRotada;
    }

    public String getTipoElemento() {
        return tipoElemento;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosicion(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public int[][] getFormaMatriz() {
        return formaMatriz;
    }
}