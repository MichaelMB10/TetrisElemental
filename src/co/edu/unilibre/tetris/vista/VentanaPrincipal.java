package co.edu.unilibre.tetris.vista;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    private JPanel panelPrincipal;
    private JButton botonUnJugador;
    private JButton botonMultijugador;
    private JButton botonInstrucciones;
    private JButton botonSalir;

    public VentanaPrincipal() {
        setTitle("Tetris Elemental - Menú Principal");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBackground(new Color(18, 18, 18)); // Fondo negro moderno
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // Título
        JLabel titulo = new JLabel("TETRIS ELEMENTAL");
        titulo.setForeground(new Color(229, 9, 20)); // Rojo contrastante
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botones
        botonUnJugador = crearBotonEstilizado("1 Jugador");
        botonMultijugador = crearBotonEstilizado("Multijugador 1v1");
        botonInstrucciones = crearBotonEstilizado("Instrucciones");
        botonSalir = crearBotonEstilizado("Salir");

        // Eventos
        botonMultijugador.addActionListener(e -> abrirVentanaJuego());
        botonSalir.addActionListener(e -> System.exit(0));

        // Agregar elementos al panel con espacios
        panelPrincipal.add(titulo);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 40)));
        panelPrincipal.add(botonUnJugador);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));
        panelPrincipal.add(botonMultijugador);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));
        panelPrincipal.add(botonInstrucciones);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));
        panelPrincipal.add(botonSalir);

        add(panelPrincipal);
    }

    private JButton crearBotonEstilizado(String texto) {
        JButton boton = new JButton(texto);
        boton.setMaximumSize(new Dimension(280, 45));
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setBackground(new Color(220, 50, 50)); // Estilo rojo contrastante
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setFocusPainted(false);
        return boton;
    }

    private void abrirVentanaJuego() {
        VentanaJuego1v1 ventanaJuego = new VentanaJuego1v1();
        ventanaJuego.setVisible(true);
        this.dispose(); // Cierra el menú principal
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaPrincipal().setVisible(true);
        });
    }
}