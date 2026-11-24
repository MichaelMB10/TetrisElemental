package co.edu.unilibre.tetris.impOperaciones;

import co.edu.unilibre.tetris.dto.JugadorDTO;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PuntajeDAO {

    private Connection obtenerConexion() throws Exception {
        OperacionesBD operacionesBD = new OperacionesBD();
        for (Method metodo : operacionesBD.getClass().getMethods()) {
            if (metodo.getParameterCount() == 0 && Connection.class.isAssignableFrom(metodo.getReturnType())) {
                Object conexion = metodo.invoke(operacionesBD);
                if (conexion instanceof Connection con) {
                    return con;
                }
            }
        }
        throw new IllegalStateException("No se encontró método de conexión en OperacionesBD");
    }

    private Integer obtenerIdJugadorPorCorreo(Connection con, String correo) throws Exception {
        String sql = "SELECT id FROM jugadores WHERE correo = ? LIMIT 1";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        return null;
    }

    public boolean guardarPuntaje(JugadorDTO jugador, int puntos) {
        if (jugador == null) {
            return false;
        }
        return guardarPuntaje(jugador.getCorreo(), puntos);
    }

    public boolean guardarPuntaje(String correoJugador, int puntos) {
        String sql = "INSERT INTO puntajes (jugador_id, puntos, modo) VALUES (?, ?, '1V1')";
        try (Connection con = obtenerConexion()) {
            Integer jugadorId = obtenerIdJugadorPorCorreo(con, correoJugador);
            if (jugadorId == null) {
                return false;
            }
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, jugadorId);
                ps.setInt(2, puntos);
                return ps.executeUpdate() > 0;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<String> obtenerTopJugadores() {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT j.nombre, p.puntos, COALESCE(p.modo, 'SOLITARIO') AS modo "
                + "FROM puntajes p "
                + "JOIN jugadores j ON j.id = p.jugador_id "
                + "ORDER BY p.puntos DESC "
                + "LIMIT 20";

        try (Connection con = obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String fila = rs.getString("nombre")
                        + " - " + rs.getInt("puntos") + " pts"
                        + " (" + rs.getString("modo") + ")";
                lista.add(fila);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lista;
    }
}