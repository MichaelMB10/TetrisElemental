package co.edu.unilibre.tetris.vista;

import co.edu.unilibre.tetris.dto.JugadorDTO;
import co.edu.unilibre.tetris.impOperaciones.JugadorDAO;

import javax.swing.*;
import java.awt.*;

public class VentanaMenu extends JFrame {

    private JTextField txtNombre;
    private JTextField txtCorreo;

    public VentanaMenu() {
        setTitle("TETRIS ELEMENTAL - Menú Principal");
        setSize(800, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panelPrincipal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(15, 15, 15));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panelPrincipal.setLayout(null);

        JLabel lblTitulo = new JLabel("TETRIS ELEMENTAL", SwingConstants.CENTER);
        lblTitulo.setBounds(150, 45, 500, 50);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 32));
        panelPrincipal.add(lblTitulo);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(250, 120, 300, 25);
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setFont(new Font("Arial", Font.BOLD, 14));
        panelPrincipal.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(250, 148, 300, 30);
        txtNombre.setFont(new Font("Arial", Font.PLAIN, 14));
        panelPrincipal.add(txtNombre);

        JLabel lblCorreo = new JLabel("Correo:");
        lblCorreo.setBounds(250, 186, 300, 25);
        lblCorreo.setForeground(Color.WHITE);
        lblCorreo.setFont(new Font("Arial", Font.BOLD, 14));
        panelPrincipal.add(lblCorreo);

        txtCorreo = new JTextField();
        txtCorreo.setBounds(250, 214, 300, 30);
        txtCorreo.setFont(new Font("Arial", Font.PLAIN, 14));
        panelPrincipal.add(txtCorreo);

        JButton btnSolitario = crearBotonRojo("Solitario", 275);
        JButton btnMultijugador = crearBotonRojo("Multijugador 1v1", 332);
        JButton btnRanking = crearBotonRojo("Ranking", 389);
        JButton btnOpciones = crearBotonRojo("Opciones", 446);
        JButton btnSalir = crearBotonRojo("Salir", 503);

        panelPrincipal.add(btnSolitario);
        panelPrincipal.add(btnMultijugador);
        panelPrincipal.add(btnRanking);
        panelPrincipal.add(btnOpciones);
        panelPrincipal.add(btnSalir);

        btnSolitario.addActionListener(e -> {
            if (validarYRegistrarJugador()) {
                new VentanaPrincipal(txtNombre.getText().trim(), txtCorreo.getText().trim()).setVisible(true);
                this.dispose();
            }
        });

        btnMultijugador.addActionListener(e -> {
            if (validarYRegistrarJugador()) {
                new VentanaJuego1v1(txtNombre.getText().trim(), txtCorreo.getText().trim()).setVisible(true);
                this.dispose();
            }
        });

        btnRanking.addActionListener(e -> {
            new VentanaRanking().setVisible(true);
            this.dispose();
        });

        btnOpciones.addActionListener(e -> JOptionPane.showMessageDialog(
                this,
                "CONTROLES E INSTRUCCIONES:\n\n"
                        + "Jugador 1 (Fuego): Flechas Izquierda / Derecha / Abajo / Arriba\n"
                        + "Jugador 2 (Hielo): Teclas A / D / S / W\n"
                        + "- Fuego: destruye un área 3x3 al fijarse.\n"
                        + "- Hielo: ralentiza la caída.\n"
                        + "- 4 líneas simultáneas en 1v1: envía 2 líneas de castigo al rival.",
                "Opciones",
                JOptionPane.INFORMATION_MESSAGE
        ));

        btnSalir.addActionListener(e -> System.exit(0));

        add(panelPrincipal);
    }

    private JButton crearBotonRojo(String texto, int y) {
        JButton boton = new JButton(texto);
        boton.setBounds(250, y, 300, 44);
        boton.setBackground(new Color(220, 53, 69));
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createLineBorder(new Color(180, 40, 50), 2));
        return boton;
    }

    private boolean validarYRegistrarJugador() {
        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();

        if (nombre.isEmpty() || correo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingresa tu nombre y correo.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            JugadorDAO dao = new JugadorDAO();
            JugadorDTO jugador = dao.consultarPorCorreo(correo);
            if (jugador == null) {
                jugador = new JugadorDTO(nombre, correo);
                dao.registrarJugador(jugador);
            }
            return true;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error conectando con la base de datos: " + ex.getMessage(),
                    "Error BD",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaMenu().setVisible(true));
    }
}