package co.edu.unilibre.tetris;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import co.edu.unilibre.tetris.modelo.GestorColisiones;

public class GestorColisionesTest {

    @Test
    public void testDetectarColisionParedReal() {
        GestorColisiones gestor = new GestorColisiones(10, 20);

        // Intentar validar si una coordenada fuera de los límites (ej. X = -1) genera colisión
        boolean colisionDetectada = gestor.verificarColisionExterna(-1, 5);

        // La prueba exige que SÍ detecte la colisión (retorne true)
        assertTrue(colisionDetectada, "Debe detectar colisión cuando la pieza cruza el límite de la pared");
    }
}