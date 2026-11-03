package co.edu.unilibre.tetris.vista;

import javax.swing.*;
import java.awt.*;

public class VentanaJuego1v1 extends JFrame {

    public VentanaJuego1v1() {
        setTitle("Tetris Elemental - Multijugador 1v1");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        inicializarInterfazJuego();
    }

    private void inicializarInterfazJuego() {
        // Layout principal dividido en dos mitades (Jugador 1 y Jugador 2)
        setLayout(new GridLayout(1, 2, 10, 10));

        // Panel Jugador 1 (Izquierda)
        JPanel panelJugador1 = crearPanelJugador("Jugador 1 (Flechas)");

        // Panel Jugador 2 (Derecha)
        JPanel panelJugador2 = crearPanelJugador("Jugador 2 (WASD)");

        add(panelJugador1);
        add(panelJugador2);
    }

    private JPanel crearPanelJugador(String nombreJugador) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(40, 40, 40));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Etiqueta superior con el nombre del jugador y su puntaje
        JLabel etiquetaInfo = new JLabel(nombreJugador + " | Puntos: 0", SwingConstants.CENTER);
        etiquetaInfo.setForeground(Color.WHITE);
        etiquetaInfo.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(etiquetaInfo, BorderLayout.NORTH);

        // Simulación del Tablero de Juego (Panel central donde caerán las piezas)
        JPanel tableroVisual = new JPanel();
        tableroVisual.setBackground(Color.BLACK);
        tableroVisual.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        panel.add(tableroVisual, BorderLayout.CENTER);

        // Panel inferior para controles o estado de elementos (Fuego/Hielo)
        JLabel etiquetaEstado = new JLabel("Elemento activo: Ninguno", SwingConstants.CENTER);
        etiquetaEstado.setForeground(new Color(200, 200, 200));
        etiquetaEstado.setFont(new Font("Arial", Font.ITALIC, 12));
        panel.add(etiquetaEstado, BorderLayout.SOUTH);

        return panel;
    }
}