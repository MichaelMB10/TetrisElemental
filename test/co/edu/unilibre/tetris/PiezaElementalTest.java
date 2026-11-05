package co.edu.unilibre.tetris;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import co.edu.unilibre.tetris.modelo.PiezaElemental;

public class PiezaElementalTest {

    @Test
    public void testActivarPoderElementalReal() {
        // Creamos una pieza real de tipo FUEGO con una duración real de 3 turnos
        PiezaElemental pieza = new PiezaElemental("FUEGO", 3);

        // Intentamos activar el efecto especial en el motor del juego
        boolean activado = pieza.activarEfecto();

        // La prueba exige que el poder se active exitosamente
        assertTrue(activado, "El poder elemental de fuego debería activarse y retornar true");
    }
}