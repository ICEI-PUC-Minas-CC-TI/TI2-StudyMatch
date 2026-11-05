package com.studymatch.model;

public class Disciplina {

    private int idDisciplina;
    private String codigo;
    private String nome;

    public Disciplina() {
    }

    public Disciplina(String codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public Disciplina(int idDisciplina, String codigo, String nome) {
        this.idDisciplina = idDisciplina;
        this.codigo = codigo;
        this.nome = nome;
    }

    public int getIdDisciplina() { return idDisciplina; }
    public void setIdDisciplina(int idDisciplina) { this.idDisciplina = idDisciplina; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    @Override
    public String toString() {
        return "Disciplina{idDisciplina=" + idDisciplina + ", codigo='" + codigo + "', nome='" + nome + "'}";
    }
}
