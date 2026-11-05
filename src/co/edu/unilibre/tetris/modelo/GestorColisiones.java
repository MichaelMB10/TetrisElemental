package co.edu.unilibre.tetris.modelo;

public class GestorColisiones {
    private int anchoTablero;
    private int altoTablero;

    public GestorColisiones(int anchoTablero, int altoTablero) {
        this.anchoTablero = anchoTablero;
        this.altoTablero = altoTablero;
    }

    public boolean verificarColisionExterna(int x, int y) {
        // Lógica real: aquí evaluaremos las coordenadas de la matriz en el futuro.
        // Como aún no implementamos el algoritmo completo de límites, retorna false.
        // Esto hace que la prueba falle de manera natural (esperaba true, obtuvo false).
        return false;
    }
}