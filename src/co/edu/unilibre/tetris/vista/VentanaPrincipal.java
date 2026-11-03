package co.edu.unilibre.tetris.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaPrincipal extends JFrame {

    private JButton botonMultijugador;
    private JButton botonRanking;
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
        // Panel principal con diseño de cuadrícula
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridBagLayout());
        panelPrincipal.setBackground(new Color(30, 30, 30)); // Fondo oscuro estilo gamer
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;

        // Título
        JLabel etiquetaTitulo = new JLabel("TETRIS ELEMENTAL");
        etiquetaTitulo.setForeground(Color.WHITE);
        etiquetaTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        gbc.gridy = 0;
        panelPrincipal.add(etiquetaTitulo, gbc);

        // Botón Multijugador 1v1
        botonMultijugador = crearBotonEstilizado("Jugar Multijugador 1v1");
        gbc.gridy = 1;
        panelPrincipal.add(botonMultijugador, gbc);

        // Botón Ranking Histórico
        botonRanking = crearBotonEstilizado("Ver Ranking Histórico");
        gbc.gridy = 2;
        panelPrincipal.add(botonRanking, gbc);

        // Botón Salir
        botonSalir = crearBotonEstilizado("Salir");
        gbc.gridy = 3;
        panelPrincipal.add(botonSalir, gbc);

        // Acciones de los botones
        botonMultijugador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirVentanaJuego();
            }
        });

        botonSalir.addActionListener(e -> System.exit(0));

        add(panelPrincipal);
    }

    private JButton crearBotonEstilizado(String texto) {
        JButton boton = new JButton(texto);
        boton.setPreferredSize(new Dimension(220, 40));
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
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}