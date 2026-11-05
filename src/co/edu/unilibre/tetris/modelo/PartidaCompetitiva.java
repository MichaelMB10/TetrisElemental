package co.edu.unilibre.tetris.modelo;

public class PartidaCompetitiva {
    private int idPartida;
    private boolean enCurso;

    public PartidaCompetitiva(int idPartida) {
        this.idPartida = idPartida;
        this.enCurso = true;
    }

    public boolean evaluarCondicionFin() {
        return !enCurso;
    }

    public void finalizarPartida() {
        this.enCurso = false;
    }
}