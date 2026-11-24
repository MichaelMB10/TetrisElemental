package co.edu.unilibre.tetris.impOperaciones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OperacionesBD {
    private static final String URL = "jdbc:postgresql://ep-polished-mud-bd9ciox6-pooler.c-8.us-east-2.aws.neon.tech/neondb?sslmode=require&channel_binding=require";
    private static final String USUARIO = "neondb_owner";
    private static final String PASSWORD = "npg_cR6Nq3iWmoHb";

    public static Connection obtenerConexion() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USUARIO, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean conectarMySQL() {
        try (Connection con = obtenerConexion()) {
            return con != null && !con.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}