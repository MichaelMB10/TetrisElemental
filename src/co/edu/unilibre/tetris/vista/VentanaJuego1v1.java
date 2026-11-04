package co.edu.unilibre.tetris.vista;

import javax.swing.*;
import java.awt.*;

public class VentanaJuego1v1 extends JFrame {

    public VentanaJuego1v1() {
        setTitle("Tetris Elemental - Modo 1v1");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        inicializarVentanaJuego();
    }

    private void inicializarVentanaJuego() {
        // Panel principal dividido para dos jugadores (Split-Screen)
        JPanel panelContenedor = new JPanel(new GridLayout(1, 2));

        // Tablero Jugador 1 (Izquierda)
        JPanel panelJugador1 = new JPanel();
        panelJugador1.setBackground(new Color(30, 30, 30));
        panelJugador1.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.RED), "Jugador 1 (Fuego 🔥)",
                0, 0, new Font("Arial", Font.BOLD, 14), Color.WHITE));

        // Tablero Jugador 2 (Derecha)
        JPanel panelJugador2 = new JPanel();
        panelJugador2.setBackground(new Color(30, 30, 30));
        panelJugador2.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.CYAN), "Jugador 2 (Hielo 🧊)",
                0, 0, new Font("Arial", Font.BOLD, 14), Color.WHITE));

        panelContenedor.add(panelJugador1);
        panelContenedor.add(panelJugador2);

        add(panelContenedor);
    }
}