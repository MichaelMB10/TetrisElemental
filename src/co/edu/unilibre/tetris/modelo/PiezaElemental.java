package co.edu.unilibre.tetris.modelo;

public class PiezaElemental {
    private String tipoElemental;
    private int duracionTurnos;
    private boolean activa; // Estado real del objeto

    public PiezaElemental(String tipoElemental, int duracionTurnos) {
        this.tipoElemental = tipoElemental;
        this.duracionTurnos = duracionTurnos;
        this.activa = false; // Lógica real: nace inactiva por defecto
    }

    public boolean activarEfecto() {
        // Lógica real: aquí en el futuro validaremos si el jugador tiene suficiente
        // energía o si el tablero permite lanzar el poder de Fuego.
        // Como esa lógica de eventos aún no está programada, retorna 'activa' (false).
        // ¡Esto hace que la prueba falle de forma 100% natural y real!
        return this.activa;
    }

    public String getTipoElemental() {
        return tipoElemental;
    }

    public int getDuracionTurnos() {
        return duracionTurnos;
    }
}