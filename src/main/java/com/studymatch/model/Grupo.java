package com.studymatch.model;

import java.time.LocalDateTime;

public class Grupo {

    private int idGrupo;
    private String nome;
    private String objetivo;
    private String modalidade;
    private String descricao;
    private LocalDateTime dataCriacao;
    private int limiteParticipantes;
    private int idDisciplina;
    private String cpfCriador;

    public Grupo() {
    }

    public Grupo(String nome, String objetivo, String modalidade, String descricao,
                 int limiteParticipantes, int idDisciplina, String cpfCriador) {
        this.nome = nome;
        this.objetivo = objetivo;
        this.modalidade = modalidade;
        this.descricao = descricao;
        this.limiteParticipantes = limiteParticipantes;
        this.idDisciplina = idDisciplina;
        this.cpfCriador = cpfCriador;
    }

    public Grupo(int idGrupo, String nome, String objetivo, String modalidade, String descricao,
                 LocalDateTime dataCriacao, int limiteParticipantes, int idDisciplina, String cpfCriador) {
        this.idGrupo = idGrupo;
        this.nome = nome;
        this.objetivo = objetivo;
        this.modalidade = modalidade;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
        this.limiteParticipantes = limiteParticipantes;
        this.idDisciplina = idDisciplina;
        this.cpfCriador = cpfCriador;
    }

    public int getIdGrupo() { return idGrupo; }
    public void setIdGrupo(int idGrupo) { this.idGrupo = idGrupo; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getObjetivo() { return objetivo; }
    public void setObjetivo(String objetivo) { this.objetivo = objetivo; }
    public String getModalidade() { return modalidade; }
    public void setModalidade(String modalidade) { this.modalidade = modalidade; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
    public int getLimiteParticipantes() { return limiteParticipantes; }
    public void setLimiteParticipantes(int limiteParticipantes) { this.limiteParticipantes = limiteParticipantes; }
    public int getIdDisciplina() { return idDisciplina; }
    public void setIdDisciplina(int idDisciplina) { this.idDisciplina = idDisciplina; }
    public String getCpfCriador() { return cpfCriador; }
    public void setCpfCriador(String cpfCriador) { this.cpfCriador = cpfCriador; }

    @Override
    public String toString() {
        return "Grupo{idGrupo=" + idGrupo + ", nome='" + nome + "', objetivo='" + objetivo +
                "', modalidade='" + modalidade + "', limiteParticipantes=" + limiteParticipantes +
                ", idDisciplina=" + idDisciplina + ", cpfCriador='" + cpfCriador + "'}";
    }
}
