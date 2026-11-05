package co.edu.unilibre.tetris;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import co.edu.unilibre.tetris.impOperaciones.OperacionesBD;

public class OperacionesBDTest {

    @Test
    public void testConexionBaseDatosReal() {
        OperacionesBD db = new OperacionesBD();

        // La prueba exige que la conexión JDBC esté activa y conectada a MySQL
        boolean estadoConexion = db.conectarMySQL();

        assertTrue(estadoConexion, "La conexión a la base de datos de Tetris Elemental debe establecerse con éxito");
    }
}