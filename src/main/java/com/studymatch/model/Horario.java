package com.studymatch.model;

import java.time.LocalTime;

public class Horario {

    private int idHorario;
    private String diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFim;

    public Horario() {
    }

    public Horario(String diaSemana, LocalTime horaInicio, LocalTime horaFim) {
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
    }

    public Horario(int idHorario, String diaSemana, LocalTime horaInicio, LocalTime horaFim) {
        this.idHorario = idHorario;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
    }

    public int getIdHorario() { return idHorario; }
    public void setIdHorario(int idHorario) { this.idHorario = idHorario; }
    public String getDiaSemana() { return diaSemana; }
    public void setDiaSemana(String diaSemana) { this.diaSemana = diaSemana; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }
    public LocalTime getHoraFim() { return horaFim; }
    public void setHoraFim(LocalTime horaFim) { this.horaFim = horaFim; }

    @Override
    public String toString() {
        return "Horario{idHorario=" + idHorario + ", diaSemana='" + diaSemana + "', horaInicio=" + horaInicio + ", horaFim=" + horaFim + "}";
    }
}
