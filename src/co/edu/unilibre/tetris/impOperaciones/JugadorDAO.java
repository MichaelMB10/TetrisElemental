package co.edu.unilibre.tetris.impOperaciones;

import co.edu.unilibre.tetris.dto.JugadorDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JugadorDAO {

    public boolean registrarJugador(JugadorDTO jugador) {
        String sql = "INSERT INTO jugadores (nombre, correo) VALUES (?, ?)";
        try (Connection con = OperacionesBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, jugador.getNombre());
            ps.setString(2, jugador.getCorreo());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public JugadorDTO consultarPorCorreo(String correo) {
        String sql = "SELECT * FROM jugadores WHERE correo = ?";
        try (Connection con = OperacionesBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new JugadorDTO(rs.getInt("id"), rs.getString("nombre"), rs.getString("correo"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método añadido para que coincida exactamente con la prueba JugadorDAOTest
    public boolean eliminarJugador(String nombre) {
        String sql = "DELETE FROM jugadores WHERE nombre = ?";
        try (Connection con = OperacionesBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}