package co.edu.unilibre.tetris.modelo;

public class TableroJuego {
    private int ancho;
    private int alto;

    public TableroJuego(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;
    }

    public int getAncho() {
        return 0; // Falla a propósito para la Fase Roja
    }

    public int getAlto() {
        return 0; // Falla a propósito para la Fase Roja
    }

    public boolean moverPieza(int x, int y) {
        return true; // Falla el assertFalse a propósito
    }

    public void llenarFilaPrueba(int fila) {
        // Stub vacío para la prueba
    }

    public int procesarLineasCompletas() {
        return 0; // Falla el assertEquals a propósito
    }
}