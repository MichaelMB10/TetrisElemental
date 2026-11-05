package co.edu.unilibre.tetris;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import co.edu.unilibre.tetris.impOperaciones.JugadorDAO;
import co.edu.unilibre.tetris.dto.JugadorDTO;

public class JugadorDAOTest {

    @Test
    public void testRegistrarJugadorReal() {
        JugadorDAO dao = new JugadorDAO();
        JugadorDTO jugador = new JugadorDTO("Michael", "michaele-bolanosc@unilibre.edu.co");
        boolean resultado = dao.registrarJugador(jugador);
        assertTrue(resultado, "Debe registrar al jugador exitosamente");
    }

    @Test
    public void testConsultarJugadorPorCorreoReal() {
        JugadorDAO dao = new JugadorDAO();
        JugadorDTO jugadorEncontrado = dao.consultarPorCorreo("michaele-bolanosc@unilibre.edu.co");
        assertNotNull(jugadorEncontrado, "El DAO debe retornar un objeto DTO válido al buscar por correo");
    }

    @Test
    public void testEliminarJugadorReal() {
        JugadorDAO dao = new JugadorDAO();
        boolean eliminado = dao.eliminarJugador("Michael");
        assertTrue(eliminado, "Debe eliminar el registro del jugador de la base de datos");
    }
}