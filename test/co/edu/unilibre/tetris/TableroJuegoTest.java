package co.edu.unilibre.tetris;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import co.edu.unilibre.tetris.modelo.TableroJuego;

public class TableroJuegoTest {
    private TableroJuego tablero;

    @BeforeEach
    public void setUp() {
        tablero = new TableroJuego(20, 10); // 20 filas de alto, 10 columnas de ancho (Estándar de Tetris)
    }

    @Test
    public void testValidarDimensionesTablero() {
        Assertions.assertEquals(10, tablero.getAncho(), "El tablero debe tener un ancho de 10 columnas");
        Assertions.assertEquals(20, tablero.getAlto(), "El tablero debe tener un alto de 20 filas");
    }

    @Test
    public void testColisionFueraDeLimitesIzquierda() {
        // Intentar mover una pieza a la coordenada X = -1 debe retornar colisión/falso
        boolean movimientoValido = tablero.moverPieza(-1, 5);
        Assertions.assertFalse(movimientoValido, "No se permite mover la pieza fuera del borde izquierdo");
    }

    @Test
    public void testEliminarLineaCompleta() {
        // Simula llenar una fila completa y verifica que el contador de líneas detecte la eliminación
        tablero.llenarFilaPrueba(19);
        int lineasEliminadas = tablero.procesarLineasCompletas();
        Assertions.assertEquals(1, lineasEliminadas, "Debe detectar y eliminar exactamente 1 línea completa");
    }
}