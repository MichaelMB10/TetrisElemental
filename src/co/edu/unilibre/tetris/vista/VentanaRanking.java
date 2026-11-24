package co.edu.unilibre.tetris.vista;

import co.edu.unilibre.tetris.impOperaciones.OperacionesBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class VentanaRanking extends JFrame {

    public VentanaRanking() {
        setTitle("TETRIS ELEMENTAL - Ranking Histórico");
        setSize(780, 540);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panelFondo = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(15, 15, 15));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        setContentPane(panelFondo);

        JLabel lblTitulo = new JLabel("RANKING HISTÓRICO DE PUNTUACIONES", SwingConstants.CENTER);
        lblTitulo.setBounds(90, 20, 600, 35);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        panelFondo.add(lblTitulo);

        DefaultTableModel modeloTabla = new DefaultTableModel(
                new Object[]{"Posición", "Jugador", "Puntaje", "Modo"},
                0
        );
        JTable tablaRanking = new JTable(modeloTabla);
        tablaRanking.setBackground(new Color(28, 28, 28));
        tablaRanking.setForeground(Color.WHITE);
        tablaRanking.setFont(new Font("Arial", Font.PLAIN, 13));
        tablaRanking.setRowHeight(26);

        cargarPuntajes(modeloTabla);

        JScrollPane scroll = new JScrollPane(tablaRanking);
        scroll.setBounds(70, 75, 640, 350);
        panelFondo.add(scroll);

        JButton btnVolver = new JButton("VOLVER");
        btnVolver.setBounds(580, 445, 130, 40);
        btnVolver.setBackground(new Color(220, 53, 69));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFont(new Font("Arial", Font.BOLD, 12));
        btnVolver.setFocusPainted(false);
        btnVolver.addActionListener(e -> {
            new VentanaMenu().setVisible(true);
            dispose();
        });
        panelFondo.add(btnVolver);
    }

    private Connection obtenerConexionBD() throws Exception {
        OperacionesBD operacionesBD = new OperacionesBD();
        for (Method m : operacionesBD.getClass().getMethods()) {
            if (m.getParameterCount() == 0 && Connection.class.isAssignableFrom(m.getReturnType())) {
                Object con = m.invoke(operacionesBD);
                if (con instanceof Connection connection) {
                    return connection;
                }
            }
        }
        throw new IllegalStateException("No se encontró un método de conexión en OperacionesBD");
    }

    private void cargarPuntajesConColumna(DefaultTableModel modeloTabla, String columnaPuntaje) throws Exception {
        String sql = "SELECT j.nombre, p." + columnaPuntaje + ", COALESCE(p.modo, 'SOLITARIO') AS modo "
                + "FROM puntajes p "
                + "JOIN jugadores j ON j.id = p.jugador_id "
                + "ORDER BY p." + columnaPuntaje + " DESC "
                + "LIMIT 20";

        try (Connection con = obtenerConexionBD();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            int posicion = 1;
            boolean hayFilas = false;
            while (rs.next()) {
                hayFilas = true;
                String nombre = rs.getString("nombre");
                int puntaje = rs.getInt(2);
                String modo = rs.getString("modo");
                modeloTabla.addRow(new Object[]{posicion++, nombre, puntaje, modo});
            }
            if (!hayFilas) {
                modeloTabla.addRow(new Object[]{"-", "Aún no hay puntajes registrados", 0, "-"});
            }
        }
    }

    private void cargarPuntajes(DefaultTableModel modeloTabla) {
        try {
            cargarPuntajesConColumna(modeloTabla, "puntaje");
        } catch (Exception exPuntaje) {
            try {
                cargarPuntajesConColumna(modeloTabla, "puntos");
            } catch (Exception exPuntos) {
                modeloTabla.addRow(new Object[]{"!", "Error cargando ranking", 0, exPuntos.getMessage()});
            }
        }
    }
}