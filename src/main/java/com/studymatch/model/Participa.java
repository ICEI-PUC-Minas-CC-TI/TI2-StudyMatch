package com.studymatch.model;

import java.time.LocalDateTime;

public class Participa {

    private String cpf;
    private int idGrupo;
    private LocalDateTime dataEntrada;

    public Participa() {
    }

    public Participa(String cpf, int idGrupo) {
        this.cpf = cpf;
        this.idGrupo = idGrupo;
    }

    public Participa(String cpf, int idGrupo, LocalDateTime dataEntrada) {
        this.cpf = cpf;
        this.idGrupo = idGrupo;
        this.dataEntrada = dataEntrada;
    }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public int getIdGrupo() { return idGrupo; }
    public void setIdGrupo(int idGrupo) { this.idGrupo = idGrupo; }
    public LocalDateTime getDataEntrada() { return dataEntrada; }
    public void setDataEntrada(LocalDateTime dataEntrada) { this.dataEntrada = dataEntrada; }

    @Override
    public String toString() {
        return "Participa{cpf='" + cpf + "', idGrupo=" + idGrupo + ", dataEntrada=" + dataEntrada + "}";
    }
}
