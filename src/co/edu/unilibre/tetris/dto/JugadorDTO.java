package co.edu.unilibre.tetris.dto;

public class JugadorDTO {
    private int id;
    private String nombre;
    private String correo;

    // Constructor vacío
    public JugadorDTO() {
    }

    // Constructor con ID, nombre y correo
    public JugadorDTO(int id, String nombre, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
    }

    // Constructor sin ID (para registros nuevos)
    public JugadorDTO(String nombre, String correo) {
        this.nombre = nombre;
        this.correo = correo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}