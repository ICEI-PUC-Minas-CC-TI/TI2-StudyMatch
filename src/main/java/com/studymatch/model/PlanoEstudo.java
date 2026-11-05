package com.studymatch.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PlanoEstudo {

    private int idPlano;
    private String cpf;
    private int idDisciplina;
    private LocalDateTime dataCriacao;
    private LocalDate dataProva;
    private String tempoDisponivel;
    private String dificuldades;
    private String conteudo;

    public PlanoEstudo() {
    }

    public PlanoEstudo(String cpf, int idDisciplina, LocalDate dataProva,
                        String tempoDisponivel, String dificuldades, String conteudo) {
        this.cpf = cpf;
        this.idDisciplina = idDisciplina;
        this.dataProva = dataProva;
        this.tempoDisponivel = tempoDisponivel;
        this.dificuldades = dificuldades;
        this.conteudo = conteudo;
    }

    public PlanoEstudo(int idPlano, String cpf, int idDisciplina, LocalDateTime dataCriacao,
                        LocalDate dataProva, String tempoDisponivel, String dificuldades, String conteudo) {
        this.idPlano = idPlano;
        this.cpf = cpf;
        this.idDisciplina = idDisciplina;
        this.dataCriacao = dataCriacao;
        this.dataProva = dataProva;
        this.tempoDisponivel = tempoDisponivel;
        this.dificuldades = dificuldades;
        this.conteudo = conteudo;
    }

    public int getIdPlano() { return idPlano; }
    public void setIdPlano(int idPlano) { this.idPlano = idPlano; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public int getIdDisciplina() { return idDisciplina; }
    public void setIdDisciplina(int idDisciplina) { this.idDisciplina = idDisciplina; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
    public LocalDate getDataProva() { return dataProva; }
    public void setDataProva(LocalDate dataProva) { this.dataProva = dataProva; }
    public String getTempoDisponivel() { return tempoDisponivel; }
    public void setTempoDisponivel(String tempoDisponivel) { this.tempoDisponivel = tempoDisponivel; }
    public String getDificuldades() { return dificuldades; }
    public void setDificuldades(String dificuldades) { this.dificuldades = dificuldades; }
    public String getConteudo() { return conteudo; }
    public void setConteudo(String conteudo) { this.conteudo = conteudo; }

    @Override
    public String toString() {
        return "PlanoEstudo{idPlano=" + idPlano + ", cpf='" + cpf + "', idDisciplina=" + idDisciplina +
                ", dataProva=" + dataProva + ", tempoDisponivel='" + tempoDisponivel + "'}";
    }
}
