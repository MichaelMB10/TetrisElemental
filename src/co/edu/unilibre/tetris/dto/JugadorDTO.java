package co.edu.unilibre.tetris.dto;

public class JugadorDTO {
    private String nombre;
    private String alias;

    public JugadorDTO(String nombre, String alias) {
        this.nombre = nombre;
        this.alias = alias;
    }

    public String getNombre() {
        return nombre;
    }

    public String getAlias() {
        return alias;
    }
}