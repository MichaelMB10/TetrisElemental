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

public class VentanaJuego1v1 extends JFrame {

    private static final int TAMANO_BLOQUE = 22;
    private static final int INTERVALO_FPS = 16;
    private static final int VELOCIDAD_CAIDA_MS = 450;

    private static final int[][][] FORMAS_PIEZAS = {
            {{1, 1, 1, 1}},
            {{1, 1}, {1, 1}},
            {{0, 1, 0}, {1, 1, 1}},
            {{1, 0, 0}, {1, 1, 1}},
            {{0, 0, 1}, {1, 1, 1}},
            {{0, 1, 1}, {1, 1, 0}},
            {{1, 1, 0}, {0, 1, 1}}
    };

    private final String nombreJugador1;
    private final String correoJugador1;
    private final Random aleatorio = new Random();

    private final TableroJuego tableroJ1;
    private final GestorColisiones colisionesJ1;
    private PiezaElemental piezaJ1;
    private int puntajeJ1 = 0;
    private boolean juegoTerminadoJ1 = false;

    private final TableroJuego tableroJ2;
    private final GestorColisiones colisionesJ2;
    private PiezaElemental piezaJ2;
    private int puntajeJ2 = 0;
    private boolean juegoTerminadoJ2 = false;

    private JPanel panelJ1;
    private JPanel panelJ2;
    private JLabel lblPuntajeJ1;
    private JLabel lblPuntajeJ2;
    private Timer temporizadorCaida;
    private Timer temporizadorRenderizado;

    public VentanaJuego1v1(String nombreJugador1, String correoJugador1) {
        this.nombreJugador1 = nombreJugador1;
        this.correoJugador1 = correoJugador1;

        this.tableroJ1 = new TableroJuego(20, 10);
        this.colisionesJ1 = new GestorColisiones();
        this.piezaJ1 = crearNuevaPieza("FUEGO");

        this.tableroJ2 = new TableroJuego(20, 10);
        this.colisionesJ2 = new GestorColisiones();
        this.piezaJ2 = crearNuevaPieza("HIELO");

        configurarVentana();
        inicializarComponentes();
        configurarControles();
        iniciarBucles();
    }

    private void configurarVentana() {
        setTitle("TETRIS ELEMENTAL - 1v1 (Fuego vs Hielo)");
        setSize(980, 680);
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

        JLabel lblTitulo = new JLabel("TETRIS ELEMENTAL - 1v1", SwingConstants.CENTER);
        lblTitulo.setBounds(220, 15, 520, 35);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        panelFondo.add(lblTitulo);

        JButton btnRanking = new JButton("RANKING");
        btnRanking.setBounds(670, 18, 120, 35);
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
        btnVolver.setBounds(810, 18, 120, 35);
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

        JLabel lblTituloJ1 = new JLabel("Jugador 1 (Fuego): " + nombreJugador1, SwingConstants.CENTER);
        lblTituloJ1.setBounds(50, 60, 220, 25);
        lblTituloJ1.setForeground(new Color(220, 53, 69));
        lblTituloJ1.setFont(new Font("Arial", Font.BOLD, 12));
        panelFondo.add(lblTituloJ1);

        panelJ1 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, getWidth(), getHeight());
                dibujarTablero(g, tableroJ1, new Color(200, 60, 40));
                dibujarPieza(g, piezaJ1, Color.ORANGE);
            }
        };
        panelJ1.setBounds(50, 90, 220, 440);
        panelJ1.setBackground(Color.BLACK);
        panelJ1.setBorder(BorderFactory.createLineBorder(new Color(220, 53, 69), 3));
        panelFondo.add(panelJ1);

        lblPuntajeJ1 = crearMarcador(panelFondo, 285, 90, new Color(220, 53, 69));

        JLabel lblTituloJ2 = new JLabel("Jugador 2 (Hielo)", SwingConstants.CENTER);
        lblTituloJ2.setBounds(510, 60, 220, 25);
        lblTituloJ2.setForeground(new Color(0, 210, 220));
        lblTituloJ2.setFont(new Font("Arial", Font.BOLD, 12));
        panelFondo.add(lblTituloJ2);

        panelJ2 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, getWidth(), getHeight());
                dibujarTablero(g, tableroJ2, new Color(0, 140, 180));
                dibujarPieza(g, piezaJ2, new Color(110, 235, 255));
            }
        };
        panelJ2.setBounds(510, 90, 220, 440);
        panelJ2.setBackground(Color.BLACK);
        panelJ2.setBorder(BorderFactory.createLineBorder(new Color(0, 210, 220), 3));
        panelFondo.add(panelJ2);

        lblPuntajeJ2 = crearMarcador(panelFondo, 745, 90, new Color(0, 210, 220));
    }

    private JLabel crearMarcador(JPanel contenedor, int x, int y, Color colorBorde) {
        JPanel caja = new JPanel(null);
        caja.setBounds(x, y, 120, 85);
        caja.setBackground(Color.BLACK);
        caja.setBorder(BorderFactory.createLineBorder(colorBorde, 2));

        JLabel lblTitulo = new JLabel("PUNTUACIÓN", SwingConstants.CENTER);
        lblTitulo.setBounds(5, 10, 110, 18);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 10));
        caja.add(lblTitulo);

        JLabel lblValor = new JLabel("0", SwingConstants.CENTER);
        lblValor.setBounds(5, 34, 110, 35);
        lblValor.setForeground(Color.WHITE);
        lblValor.setFont(new Font("Arial", Font.BOLD, 20));
        caja.add(lblValor);

        contenedor.add(caja);
        return lblValor;
    }

    private void dibujarTablero(Graphics g, TableroJuego tablero, Color colorBloque) {
        int[][] matriz = tablero.getMatrizTablero();
        if (matriz == null) {
            return;
        }
        for (int fila = 0; fila < matriz.length; fila++) {
            for (int col = 0; col < matriz[fila].length; col++) {
                if (matriz[fila][col] != 0) {
                    g.setColor(colorBloque);
                    g.fillRect(col * TAMANO_BLOQUE, fila * TAMANO_BLOQUE, TAMANO_BLOQUE - 1, TAMANO_BLOQUE - 1);
                }
            }
        }
    }

    private void dibujarPieza(Graphics g, PiezaElemental pieza, Color colorPieza) {
        if (pieza == null) {
            return;
        }
        g.setColor(colorPieza);
        int[][] forma = pieza.getFormaMatriz();
        for (int i = 0; i < forma.length; i++) {
            for (int j = 0; j < forma[i].length; j++) {
                if (forma[i][j] != 0) {
                    int x = (pieza.getPosX() + j) * TAMANO_BLOQUE;
                    int y = (pieza.getPosY() + i) * TAMANO_BLOQUE;
                    g.fillRect(x, y, TAMANO_BLOQUE - 1, TAMANO_BLOQUE - 1);
                }
            }
        }
    }

    private boolean hayColision(TableroJuego tablero, GestorColisiones gestor, PiezaElemental pieza, int nuevoX, int nuevoY) {
        if (pieza == null || tablero == null) {
            return true;
        }
        if (gestor.verificarColisionParedes(pieza, tablero, nuevoX, nuevoY)) {
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
                int codigo = e.getKeyCode();

                if (!juegoTerminadoJ1 && piezaJ1 != null) {
                    int x1 = piezaJ1.getPosX();
                    int y1 = piezaJ1.getPosY();
                    if (codigo == KeyEvent.VK_LEFT && !hayColision(tableroJ1, colisionesJ1, piezaJ1, x1 - 1, y1)) {
                        piezaJ1.moverIzquierda();
                    } else if (codigo == KeyEvent.VK_RIGHT && !hayColision(tableroJ1, colisionesJ1, piezaJ1, x1 + 1, y1)) {
                        piezaJ1.moverDerecha();
                    } else if (codigo == KeyEvent.VK_DOWN && !hayColision(tableroJ1, colisionesJ1, piezaJ1, x1, y1 + 1)) {
                        piezaJ1.moverAbajo();
                    } else if (codigo == KeyEvent.VK_UP) {
                        piezaJ1.rotar();
                        if (hayColision(tableroJ1, colisionesJ1, piezaJ1, piezaJ1.getPosX(), piezaJ1.getPosY())) {
                            piezaJ1.rotar();
                            piezaJ1.rotar();
                            piezaJ1.rotar();
                        }
                    }
                }

                if (!juegoTerminadoJ2 && piezaJ2 != null) {
                    int x2 = piezaJ2.getPosX();
                    int y2 = piezaJ2.getPosY();
                    if (codigo == KeyEvent.VK_A && !hayColision(tableroJ2, colisionesJ2, piezaJ2, x2 - 1, y2)) {
                        piezaJ2.moverIzquierda();
                    } else if (codigo == KeyEvent.VK_D && !hayColision(tableroJ2, colisionesJ2, piezaJ2, x2 + 1, y2)) {
                        piezaJ2.moverDerecha();
                    } else if (codigo == KeyEvent.VK_S && !hayColision(tableroJ2, colisionesJ2, piezaJ2, x2, y2 + 1)) {
                        piezaJ2.moverAbajo();
                    } else if (codigo == KeyEvent.VK_W) {
                        piezaJ2.rotar();
                        if (hayColision(tableroJ2, colisionesJ2, piezaJ2, piezaJ2.getPosX(), piezaJ2.getPosY())) {
                            piezaJ2.rotar();
                            piezaJ2.rotar();
                            piezaJ2.rotar();
                        }
                    }
                }
            }
        });
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

    private void iniciarBucles() {
        temporizadorRenderizado = new Timer(INTERVALO_FPS, e -> {
            panelJ1.repaint();
            panelJ2.repaint();
        });
        temporizadorRenderizado.start();

        temporizadorCaida = new Timer(VELOCIDAD_CAIDA_MS, e -> {
            if (!juegoTerminadoJ1) {
                int nuevoY1 = piezaJ1.getPosY() + 1;
                if (!hayColision(tableroJ1, colisionesJ1, piezaJ1, piezaJ1.getPosX(), nuevoY1)) {
                    piezaJ1.moverAbajo();
                } else {
                    fijarPiezaEnTablero(tableroJ1, piezaJ1, 1);
                    int lineas1 = tableroJ1.procesarLineasCompletas();
                    if (lineas1 > 0) {
                        puntajeJ1 += lineas1 * 100;
                        lblPuntajeJ1.setText(String.valueOf(puntajeJ1));
                    }
                    piezaJ1 = crearNuevaPieza("FUEGO");
                    if (hayColision(tableroJ1, colisionesJ1, piezaJ1, piezaJ1.getPosX(), piezaJ1.getPosY())) {
                        juegoTerminadoJ1 = true;
                    }
                }
            }

            if (!juegoTerminadoJ2) {
                int nuevoY2 = piezaJ2.getPosY() + 1;
                if (!hayColision(tableroJ2, colisionesJ2, piezaJ2, piezaJ2.getPosX(), nuevoY2)) {
                    piezaJ2.moverAbajo();
                } else {
                    fijarPiezaEnTablero(tableroJ2, piezaJ2, 2);
                    int lineas2 = tableroJ2.procesarLineasCompletas();
                    if (lineas2 > 0) {
                        puntajeJ2 += lineas2 * 100;
                        lblPuntajeJ2.setText(String.valueOf(puntajeJ2));
                    }
                    piezaJ2 = crearNuevaPieza("HIELO");
                    if (hayColision(tableroJ2, colisionesJ2, piezaJ2, piezaJ2.getPosX(), piezaJ2.getPosY())) {
                        juegoTerminadoJ2 = true;
                    }
                }
            }

            if (juegoTerminadoJ1 && juegoTerminadoJ2) {
                detenerBucles();
                guardarPuntajeEnBaseDatos(Math.max(puntajeJ1, puntajeJ2));
                String ganador = puntajeJ1 > puntajeJ2
                        ? "Jugador 1 (Fuego)"
                        : (puntajeJ2 > puntajeJ1 ? "Jugador 2 (Hielo)" : "Empate");
                JOptionPane.showMessageDialog(
                        this,
                        "Partida 1v1 terminada.\nGanador: " + ganador
                                + "\nFuego: " + puntajeJ1 + " pts | Hielo: " + puntajeJ2 + " pts",
                        "Fin del 1v1",
                        JOptionPane.INFORMATION_MESSAGE
                );
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

    private void guardarPuntajeEnBaseDatos(int mejorPuntaje) {
        try {
            JugadorDAO jugadorDAO = new JugadorDAO();
            Object jugador = jugadorDAO.consultarPorCorreo(correoJugador1);
            PuntajeDAO puntajeDAO = new PuntajeDAO();
            for (Method m : puntajeDAO.getClass().getMethods()) {
                String nombre = m.getName().toLowerCase();
                if (nombre.contains("guardar") || nombre.contains("registrar") || nombre.contains("insertar")) {
                    Class<?>[] params = m.getParameterTypes();
                    if (params.length == 2 && jugador != null && params[0].isAssignableFrom(jugador.getClass())) {
                        m.invoke(puntajeDAO, jugador, mejorPuntaje);
                        return;
                    }
                    if (params.length == 2 && params[0] == String.class) {
                        m.invoke(puntajeDAO, correoJugador1, mejorPuntaje);
                        return;
                    }
                }
            }
        } catch (Exception ignored) {
        }
    }

    private PiezaElemental crearNuevaPieza(String elemento) {
        int[][] base = FORMAS_PIEZAS[aleatorio.nextInt(FORMAS_PIEZAS.length)];
        int[][] copia = new int[base.length][base[0].length];
        for (int i = 0; i < base.length; i++) {
            System.arraycopy(base[i], 0, copia[i], 0, base[i].length);
        }
        return new PiezaElemental(elemento, copia);
    }
}