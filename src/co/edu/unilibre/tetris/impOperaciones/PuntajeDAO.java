package co.edu.unilibre.tetris.impOperaciones;
import java.util.List;
import java.util.ArrayList;

public class PuntajeDAO {

    public int obtenerMaximoPuntaje(String nombreJugador) {
        return 0; // Falla el assertTrue (> 0)
    }

    public boolean guardarPuntaje(String nombreJugador, int puntaje) {
        return false; // Falla el assertTrue (esperaba true)
    }

    public List<String> obtenerTopJugadores() {
        return new ArrayList<>(); // Retorna lista vacía, haciendo que falle el assertFalsy/isEmpty
    }
}