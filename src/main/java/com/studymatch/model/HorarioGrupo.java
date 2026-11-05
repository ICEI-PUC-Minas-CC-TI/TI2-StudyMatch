package com.studymatch.model;

public class HorarioGrupo {

    private int idGrupo;
    private int idHorario;

    public HorarioGrupo() {
    }

    public HorarioGrupo(int idGrupo, int idHorario) {
        this.idGrupo = idGrupo;
        this.idHorario = idHorario;
    }

    public int getIdGrupo() { return idGrupo; }
    public void setIdGrupo(int idGrupo) { this.idGrupo = idGrupo; }
    public int getIdHorario() { return idHorario; }
    public void setIdHorario(int idHorario) { this.idHorario = idHorario; }

    @Override
    public String toString() {
        return "HorarioGrupo{idGrupo=" + idGrupo + ", idHorario=" + idHorario + "}";
    }
}
