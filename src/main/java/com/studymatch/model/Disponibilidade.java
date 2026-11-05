package com.studymatch.model;

public class Disponibilidade {

    private String cpf;
    private int idHorario;

    public Disponibilidade() {
    }

    public Disponibilidade(String cpf, int idHorario) {
        this.cpf = cpf;
        this.idHorario = idHorario;
    }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public int getIdHorario() { return idHorario; }
    public void setIdHorario(int idHorario) { this.idHorario = idHorario; }

    @Override
    public String toString() {
        return "Disponibilidade{cpf='" + cpf + "', idHorario=" + idHorario + "}";
    }
}
