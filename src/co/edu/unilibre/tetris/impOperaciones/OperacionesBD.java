package co.edu.unilibre.tetris.impOperaciones;

public class OperacionesBD {

    public boolean conectarMySQL() {
        // Error realista: Lanza una excepción de entorno o estado nulo porque el driver JDBC o la URL no están configurados
        if (true) {
            throw new RuntimeException("SQLException: Access denied for user 'root'@'localhost' (Using password: NO) - Error conectando a la BD del Tetris");
        }
        return false;
    }
}