package co.edu.unilibre.tetris;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import co.edu.unilibre.tetris.modelo.TableroJuego;

public class TableroJuegoTest {
    private TableroJuego tablero;

    @BeforeEach
    public void setUp() {
        tablero = new TableroJuego(10, 20); // Ancho 10, Alto 20 estándar de Tetris
    }

    @Test
    public void testValidarDimensionesTablero() {
        assertEquals(10, tablero.getAncho(), "El tablero debe tener un ancho de 10 columnas");
        assertEquals(20, tablero.getAlto(), "El tablero debe tener un alto de 20 filas");
    }

    @Test
    public void testColisionFueraDeLimitesIzquierda() {
        // Intentar mover una pieza a la coordenada X = -1 debe retornar colisión/falso
        boolean movimientoValido = tablero.moverPieza( -1, 5 );
        assertFalse(movimientoValido, "No se permite mover la pieza fuera del borde izquierdo");
    }

    @Test
    public void testEliminarLineaCompleta() {
        // Simula llenar una fila completa y verifica que el contador de líneas disminuya o puntaje aumente
        tablero.llenarFilaPrueba(19);
        int lineasEliminadas = tablero.procesarLineasCompletas();
        assertEquals(1, lineasEliminadas, "Debe detectar y eliminar exactamente 1 línea completa");
    }
}