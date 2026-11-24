package co.edu.unilibre.tetris.vista;

import co.edu.unilibre.tetris.impOperaciones.JugadorDAO;
import co.edu.unilibre.tetris.impOperaciones.PuntajeDAO;
import co.edu.unilibre.tetris.modelo.GestorColisiones;
import co.edu.unilibre.tetris.modelo.PiezaElemental;
import co.edu.unilibre.tetris.modelo.TableroJuego;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.reflect.Method;
import java.util.Random;

public class VentanaPrincipal extends JFrame {

    private static final int TAMANO_BLOQUE = 25;
    private static final int INTERVALO_FPS = 16;
    private static final int VELOCIDAD_CAIDA_MS = 400;

    private static final int[][][] FORMAS_PIEZAS = {
            {{1, 1, 1, 1}},
            {{1, 1}, {1, 1}},
            {{0, 1, 0}, {1, 1, 1}},
            {{1, 0, 0}, {1, 1, 1}},
            {{0, 0, 1}, {1, 1, 1}},
            {{0, 1, 1}, {1, 1, 0}},
            {{1, 1, 0}, {0, 1, 1}}
    };

    private final String nombreJugador;
    private final String correoJugador;
    private final TableroJuego tablero;
    private final GestorColisiones gestorColisiones;
    private final Random aleatorio = new Random();

    private PiezaElemental piezaActual;
    private String elementoActual = "FUEGO";
    private JPanel panelJuego;
    private JLabel lblValorPuntaje;
    private JLabel lblEstadoElemento;
    private Timer temporizadorCaida;
    private Timer temporizadorRenderizado;
    private int puntuacion = 0;
    private boolean juegoTerminado = false;

    public VentanaPrincipal(String nombreJugador, String correoJugador) {
        this.nombreJugador = nombreJugador;
        this.correoJugador = correoJugador;
        this.tablero = new TableroJuego(20, 10);
        this.gestorColisiones = new GestorColisiones();
        this.piezaActual = crearNuevaPieza();

        configurarVentana();
        inicializarComponentes();
        configurarControles();
        iniciarBucles();
    }

    private void configurarVentana() {
        setTitle("TETRIS ELEMENTAL - Solitario - " + nombreJugador);
        setSize(840, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setFocusable(true);
    }

    private void inicializarComponentes() {
        JPanel panelFondo = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(15, 15, 15));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        setContentPane(panelFondo);

        JLabel lblTitulo = new JLabel("TETRIS ELEMENTAL - SOLITARIO", SwingConstants.CENTER);
        lblTitulo.setBounds(110, 18, 620, 35);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        panelFondo.add(lblTitulo);

        JLabel lblJugador = new JLabel("Jugador: " + nombreJugador, SwingConstants.CENTER);
        lblJugador.setBounds(110, 52, 620, 25);
        lblJugador.setForeground(new Color(220, 53, 69));
        lblJugador.setFont(new Font("Arial", Font.BOLD, 14));
        panelFondo.add(lblJugador);

        panelJuego = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, getWidth(), getHeight());
                dibujarTableroFijo(g);
                dibujarPiezaActual(g);
            }
        };
        panelJuego.setBounds(260, 95, 250, 500);
        panelJuego.setBackground(Color.BLACK);
        panelJuego.setBorder(BorderFactory.createLineBorder(new Color(220, 53, 69), 3));
        panelFondo.add(panelJuego);

        JPanel panelPuntuacion = new JPanel(null);
        panelPuntuacion.setBounds(545, 95, 210, 105);
        panelPuntuacion.setBackground(Color.BLACK);
        panelPuntuacion.setBorder(BorderFactory.createLineBorder(new Color(220, 53, 69), 2));

        JLabel lblTxtPuntaje = new JLabel("PUNTUACIÓN", SwingConstants.CENTER);
        lblTxtPuntaje.setBounds(10, 15, 190, 20);
        lblTxtPuntaje.setForeground(Color.WHITE);
        lblTxtPuntaje.setFont(new Font("Arial", Font.BOLD, 12));
        panelPuntuacion.add(lblTxtPuntaje);

        lblValorPuntaje = new JLabel("0", SwingConstants.CENTER);
        lblValorPuntaje.setBounds(10, 45, 190, 35);
        lblValorPuntaje.setForeground(Color.WHITE);
        lblValorPuntaje.setFont(new Font("Arial", Font.BOLD, 22));
        panelPuntuacion.add(lblValorPuntaje);
        panelFondo.add(panelPuntuacion);

        lblEstadoElemento = new JLabel("Elemento: " + elementoActual, SwingConstants.CENTER);
        lblEstadoElemento.setBounds(545, 215, 210, 35);
        lblEstadoElemento.setForeground(Color.ORANGE);
        lblEstadoElemento.setFont(new Font("Arial", Font.BOLD, 13));
        panelFondo.add(lblEstadoElemento);

        JButton btnRanking = new JButton("RANKING");
        btnRanking.setBounds(450, 610, 130, 40);
        btnRanking.setBackground(new Color(40, 40, 40));
        btnRanking.setForeground(Color.WHITE);
        btnRanking.setFont(new Font("Arial", Font.BOLD, 12));
        btnRanking.setFocusPainted(false);
        btnRanking.addActionListener(e -> {
            detenerBucles();
            new VentanaRanking().setVisible(true);
            dispose();
        });
        panelFondo.add(btnRanking);

        JButton btnVolver = new JButton("VOLVER");
        btnVolver.setBounds(600, 610, 130, 40);
        btnVolver.setBackground(new Color(220, 53, 69));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFont(new Font("Arial", Font.BOLD, 12));
        btnVolver.setFocusPainted(false);
        btnVolver.addActionListener(e -> {
            detenerBucles();
            new VentanaMenu().setVisible(true);
            dispose();
        });
        panelFondo.add(btnVolver);
    }

    private void dibujarTableroFijo(Graphics g) {
        int[][] matriz = tablero.getMatrizTablero();
        if (matriz == null) {
            return;
        }
        for (int fila = 0; fila < matriz.length; fila++) {
            for (int col = 0; col < matriz[fila].length; col++) {
                int valor = matriz[fila][col];
                if (valor != 0) {
                    if (valor == 2) {
                        g.setColor(new Color(0, 170, 210));
                    } else if (valor == 9) {
                        g.setColor(Color.DARK_GRAY);
                    } else {
                        g.setColor(new Color(200, 60, 40));
                    }
                    g.fillRect(col * TAMANO_BLOQUE, fila * TAMANO_BLOQUE, TAMANO_BLOQUE - 1, TAMANO_BLOQUE - 1);
                }
            }
        }
    }

    private void dibujarPiezaActual(Graphics g) {
        if (piezaActual == null) {
            return;
        }
        if ("HIELO".equals(elementoActual)) {
            g.setColor(new Color(110, 235, 255));
        } else {
            g.setColor(Color.ORANGE);
        }

        int[][] forma = piezaActual.getFormaMatriz();
        for (int i = 0; i < forma.length; i++) {
            for (int j = 0; j < forma[i].length; j++) {
                if (forma[i][j] != 0) {
                    int x = (piezaActual.getPosX() + j) * TAMANO_BLOQUE;
                    int y = (piezaActual.getPosY() + i) * TAMANO_BLOQUE;
                    g.fillRect(x, y, TAMANO_BLOQUE - 1, TAMANO_BLOQUE - 1);
                }
            }
        }
    }

    private boolean hayColision(PiezaElemental pieza, int nuevoX, int nuevoY) {
        if (pieza == null || tablero == null) {
            return true;
        }
        if (gestorColisiones.verificarColisionParedes(pieza, tablero, nuevoX, nuevoY)) {
            return true;
        }

        int[][] matriz = tablero.getMatrizTablero();
        int[][] forma = pieza.getFormaMatriz();
        if (matriz == null || forma == null) {
            return false;
        }

        int filas = matriz.length;
        int columnas = matriz[0].length;
        for (int i = 0; i < forma.length; i++) {
            for (int j = 0; j < forma[i].length; j++) {
                if (forma[i][j] != 0) {
                    int filaTablero = nuevoY + i;
                    int colTablero = nuevoX + j;
                    if (colTablero < 0 || colTablero >= columnas || filaTablero >= filas) {
                        return true;
                    }
                    if (filaTablero >= 0 && matriz[filaTablero][colTablero] != 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void configurarControles() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (juegoTerminado || piezaActual == null) {
                    return;
                }

                int posX = piezaActual.getPosX();
                int posY = piezaActual.getPosY();

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        if (!hayColision(piezaActual, posX - 1, posY)) {
                            piezaActual.moverIzquierda();
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (!hayColision(piezaActual, posX + 1, posY)) {
                            piezaActual.moverDerecha();
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (!hayColision(piezaActual, posX, posY + 1)) {
                            piezaActual.moverAbajo();
                        }
                        break;
                    case KeyEvent.VK_UP:
                        piezaActual.rotar();
                        if (hayColision(piezaActual, piezaActual.getPosX(), piezaActual.getPosY())) {
                            piezaActual.rotar();
                            piezaActual.rotar();
                            piezaActual.rotar();
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void iniciarBucles() {
        temporizadorRenderizado = new Timer(INTERVALO_FPS, e -> panelJuego.repaint());
        temporizadorRenderizado.start();

        temporizadorCaida = new Timer(VELOCIDAD_CAIDA_MS, e -> {
            if (juegoTerminado || piezaActual == null) {
                return;
            }
            int nuevoY = piezaActual.getPosY() + 1;
            if (!hayColision(piezaActual, piezaActual.getPosX(), nuevoY)) {
                piezaActual.moverAbajo();
            } else {
                fijarPiezaYSiguiente();
            }
        });
        temporizadorCaida.start();
    }

    private void detenerBucles() {
        if (temporizadorCaida != null) {
            temporizadorCaida.stop();
        }
        if (temporizadorRenderizado != null) {
            temporizadorRenderizado.stop();
        }
    }

    private void fijarPiezaYSiguiente() {
        int valorCelda = "HIELO".equals(elementoActual) ? 2 : 1;
        fijarPiezaEnTablero(tablero, piezaActual, valorCelda);

        int lineas = tablero.procesarLineasCompletas();
        if (lineas > 0) {
            puntuacion += lineas * 100;
            lblValorPuntaje.setText(String.valueOf(puntuacion));
        }

        piezaActual = crearNuevaPieza();
        if (hayColision(piezaActual, piezaActual.getPosX(), piezaActual.getPosY())) {
            juegoTerminado = true;
            detenerBucles();
            guardarPuntajeEnBaseDatos();
            JOptionPane.showMessageDialog(
                    this,
                    "Fin de la partida. Puntuación final: " + puntuacion,
                    "Juego Terminado",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private void fijarPiezaEnTablero(TableroJuego tableroDestino, PiezaElemental pieza, int valorCelda) {
        if (tableroDestino == null || pieza == null) {
            return;
        }
        int[][] matriz = tableroDestino.getMatrizTablero();
        int[][] forma = pieza.getFormaMatriz();
        if (matriz == null || forma == null) {
            return;
        }
        int baseX = pieza.getPosX();
        int baseY = pieza.getPosY();
        for (int i = 0; i < forma.length; i++) {
            for (int j = 0; j < forma[i].length; j++) {
                if (forma[i][j] != 0) {
                    int fila = baseY + i;
                    int col = baseX + j;
                    if (fila >= 0 && fila < matriz.length && col >= 0 && col < matriz[fila].length) {
                        matriz[fila][col] = valorCelda;
                    }
                }
            }
        }
    }

    private void guardarPuntajeEnBaseDatos() {
        try {
            JugadorDAO jugadorDAO = new JugadorDAO();
            Object jugador = jugadorDAO.consultarPorCorreo(correoJugador);
            PuntajeDAO puntajeDAO = new PuntajeDAO();
            for (Method m : puntajeDAO.getClass().getMethods()) {
                String nombre = m.getName().toLowerCase();
                if (nombre.contains("guardar") || nombre.contains("registrar") || nombre.contains("insertar")) {
                    Class<?>[] params = m.getParameterTypes();
                    if (params.length == 2 && jugador != null && params[0].isAssignableFrom(jugador.getClass())) {
                        m.invoke(puntajeDAO, jugador, puntuacion);
                        return;
                    }
                    if (params.length == 2 && params[0] == String.class) {
                        m.invoke(puntajeDAO, correoJugador, puntuacion);
                        return;
                    }
                }
            }
        } catch (Exception ignored) {
        }
    }

    private PiezaElemental crearNuevaPieza() {
        elementoActual = aleatorio.nextBoolean() ? "FUEGO" : "HIELO";
        if (lblEstadoElemento != null) {
            lblEstadoElemento.setText("Elemento: " + elementoActual);
            lblEstadoElemento.setForeground("HIELO".equals(elementoActual) ? new Color(110, 235, 255) : Color.ORANGE);
        }

        int[][] base = FORMAS_PIEZAS[aleatorio.nextInt(FORMAS_PIEZAS.length)];
        int[][] copia = new int[base.length][base[0].length];
        for (int i = 0; i < base.length; i++) {
            System.arraycopy(base[i], 0, copia[i], 0, base[i].length);
        }
        return new PiezaElemental(elementoActual, copia);
    }
}