package co.edu.unilibre.tetris.impOperaciones;
import co.edu.unilibre.tetris.dto.JugadorDTO;

public class JugadorDAO {

    public boolean registrarJugador(JugadorDTO jugador) {
        return false; // Falla la primera prueba
    }

    public JugadorDTO consultarPorCorreo(String correo) {
        return null; // Falla la segunda prueba (assertNotNull espera objeto, recibe null)
    }

    public boolean eliminarJugador(String nombre) {
        return false; // Falla la tercera prueba
    }
}