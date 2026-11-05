package com.studymatch.model;

public class Estudante {

    private String cpf;
    private String nome;
    private String email;
    private String senha;
    private String curso;
    private int semestre;
    private String modalidadePreferida;

    public Estudante() {
    }

    public Estudante(String cpf, String nome, String email, String senha, String curso,
                      int semestre, String modalidadePreferida) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.curso = curso;
        this.semestre = semestre;
        this.modalidadePreferida = modalidadePreferida;
    }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }
    public int getSemestre() { return semestre; }
    public void setSemestre(int semestre) { this.semestre = semestre; }
    public String getModalidadePreferida() { return modalidadePreferida; }
    public void setModalidadePreferida(String modalidadePreferida) { this.modalidadePreferida = modalidadePreferida; }

    @Override
    public String toString() {
        return "Estudante{cpf='" + cpf + "', nome='" + nome + "', email='" + email +
                "', curso='" + curso + "', semestre=" + semestre + ", modalidadePreferida='" + modalidadePreferida + "'}";
    }
}
