package co.edu.unilibre.tetris;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import co.edu.unilibre.tetris.impOperaciones.PuntajeDAO;

public class PuntajeDAOTest {

    @Test
    public void testObtenerMaximoPuntajeReal() {
        PuntajeDAO dao = new PuntajeDAO();
        int puntajeMax = dao.obtenerMaximoPuntaje("Michael");
        assertTrue(puntajeMax > 0, "El DAO debe retornar un puntaje máximo mayor a cero");
    }

    @Test
    public void testGuardarNuevoPuntajeReal() {
        PuntajeDAO dao = new PuntajeDAO();
        boolean guardado = dao.guardarPuntaje("Michael", 1500);
        assertTrue(guardado, "El puntaje del usuario debe almacenarse correctamente en la base de datos");
    }

    @Test
    public void testListarTopPuntajesReal() {
        PuntajeDAO dao = new PuntajeDAO();
        var ranking = dao.obtenerTopJugadores();
        assertNotNull(ranking, "La lista del ranking global no debe ser nula");
        assertFalse(ranking.isEmpty(), "El ranking debe contener registros de puntajes");
    }
}