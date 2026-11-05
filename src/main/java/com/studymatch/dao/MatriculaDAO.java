package com.studymatch.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.studymatch.model.Matricula;

public class MatriculaDAO {

    private static final String URL = "jdbc:postgresql://localhost:5432/studymatch";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "postgres";

    public Connection abrirConexao() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }

    public void fecharConexao(Connection conexao) throws SQLException {
        if (conexao != null) {
            conexao.close();
        }
    }

    public void inserir(Matricula m) throws SQLException {
        String sql = "INSERT INTO matricula (cpf, id_disciplina, nivel_conhecimento, objetivo) VALUES (?, ?, ?, ?)";

        Connection conexao = abrirConexao();
        PreparedStatement comando = conexao.prepareStatement(sql);
        comando.setString(1, m.getCpf());
        comando.setInt(2, m.getIdDisciplina());
        comando.setString(3, m.getNivelConhecimento());
        comando.setString(4, m.getObjetivo());
        comando.executeUpdate();

        comando.close();
        fecharConexao(conexao);
    }

    //Atualiza matrícula pela chave composta
    public void atualizar(Matricula m) throws SQLException {
        String sql = "UPDATE matricula SET nivel_conhecimento = ?, objetivo = ? WHERE cpf = ? AND id_disciplina = ?";

        Connection conexao = abrirConexao();
        PreparedStatement comando = conexao.prepareStatement(sql);
        comando.setString(1, m.getNivelConhecimento());
        comando.setString(2, m.getObjetivo());
        comando.setString(3, m.getCpf());
        comando.setInt(4, m.getIdDisciplina());
        comando.executeUpdate();

        comando.close();
        fecharConexao(conexao);
    }

    public void remover(String cpf, int idDisciplina) throws SQLException {
        String sql = "DELETE FROM matricula WHERE cpf = ? AND id_disciplina = ?";

        Connection conexao = abrirConexao();
        PreparedStatement comando = conexao.prepareStatement(sql);
        comando.setString(1, cpf);
        comando.setInt(2, idDisciplina);
        comando.executeUpdate();

        comando.close();
        fecharConexao(conexao);
    }

    public Matricula buscar(String cpf, int idDisciplina) throws SQLException {
        String sql = "SELECT cpf, id_disciplina, nivel_conhecimento, objetivo FROM matricula " +
                     "WHERE cpf = ? AND id_disciplina = ?";

        Connection conexao = abrirConexao();
        PreparedStatement comando = conexao.prepareStatement(sql);
        comando.setString(1, cpf);
        comando.setInt(2, idDisciplina);
        ResultSet resultado = comando.executeQuery();

        Matricula matricula = null;
        if (resultado.next()) {
            matricula = montar(resultado);
        }

        resultado.close();
        comando.close();
        fecharConexao(conexao);
        return matricula;
    }

    public List<Matricula> listarPorEstudante(String cpf) throws SQLException {
        List<Matricula> matriculas = new ArrayList<>();
        String sql = "SELECT cpf, id_disciplina, nivel_conhecimento, objetivo FROM matricula WHERE cpf = ?";

        Connection conexao = abrirConexao();
        PreparedStatement comando = conexao.prepareStatement(sql);
        comando.setString(1, cpf);
        ResultSet resultado = comando.executeQuery();

        while (resultado.next()) {
            matriculas.add(montar(resultado));
        }

        resultado.close();
        comando.close();
        fecharConexao(conexao);
        return matriculas;
    }

    public List<Matricula> listarTodos() throws SQLException {
        List<Matricula> matriculas = new ArrayList<>();
        String sql = "SELECT cpf, id_disciplina, nivel_conhecimento, objetivo FROM matricula";

        Connection conexao = abrirConexao();
        PreparedStatement comando = conexao.prepareStatement(sql);
        ResultSet resultado = comando.executeQuery();

        while (resultado.next()) {
            matriculas.add(montar(resultado));
        }

        resultado.close();
        comando.close();
        fecharConexao(conexao);
        return matriculas;
    }

    private Matricula montar(ResultSet resultado) throws SQLException {
        return new Matricula(
                resultado.getString("cpf"),
                resultado.getInt("id_disciplina"),
                resultado.getString("nivel_conhecimento"),
                resultado.getString("objetivo")
        );
    }
}
