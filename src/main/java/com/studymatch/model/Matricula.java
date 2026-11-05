package com.studymatch.model;

public class Matricula {

    private String cpf;
    private int idDisciplina;
    private String nivelConhecimento;
    private String objetivo;

    public Matricula() {
    }

    public Matricula(String cpf, int idDisciplina, String nivelConhecimento, String objetivo) {
        this.cpf = cpf;
        this.idDisciplina = idDisciplina;
        this.nivelConhecimento = nivelConhecimento;
        this.objetivo = objetivo;
    }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public int getIdDisciplina() { return idDisciplina; }
    public void setIdDisciplina(int idDisciplina) { this.idDisciplina = idDisciplina; }
    public String getNivelConhecimento() { return nivelConhecimento; }
    public void setNivelConhecimento(String nivelConhecimento) { this.nivelConhecimento = nivelConhecimento; }
    public String getObjetivo() { return objetivo; }
    public void setObjetivo(String objetivo) { this.objetivo = objetivo; }

    @Override
    public String toString() {
        return "Matricula{cpf='" + cpf + "', idDisciplina=" + idDisciplina +
                ", nivelConhecimento='" + nivelConhecimento + "', objetivo='" + objetivo + "'}";
    }
}
