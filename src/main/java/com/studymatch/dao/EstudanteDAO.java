package com.studymatch.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.studymatch.model.Estudante;

public class EstudanteDAO {

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

    public void inserir(Estudante e) throws SQLException {
        String sql = "INSERT INTO estudante (cpf, nome, email, senha, curso, semestre, modalidade_preferida) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        Connection conexao = abrirConexao();
        PreparedStatement comando = conexao.prepareStatement(sql);
        comando.setString(1, e.getCpf());
        comando.setString(2, e.getNome());
        comando.setString(3, e.getEmail());
        comando.setString(4, e.getSenha());
        comando.setString(5, e.getCurso());
        comando.setInt(6, e.getSemestre());
        comando.setString(7, e.getModalidadePreferida());
        comando.executeUpdate();

        comando.close();
        fecharConexao(conexao);
    }

    public void atualizar(Estudante e) throws SQLException {
        String sql = "UPDATE estudante SET nome = ?, email = ?, senha = ?, curso = ?, " +
                     "semestre = ?, modalidade_preferida = ? WHERE cpf = ?";

        Connection conexao = abrirConexao();
        PreparedStatement comando = conexao.prepareStatement(sql);
        comando.setString(1, e.getNome());
        comando.setString(2, e.getEmail());
        comando.setString(3, e.getSenha());
        comando.setString(4, e.getCurso());
        comando.setInt(5, e.getSemestre());
        comando.setString(6, e.getModalidadePreferida());
        comando.setString(7, e.getCpf());
        comando.executeUpdate();

        comando.close();
        fecharConexao(conexao);
    }

    public void remover(String cpf) throws SQLException {
        String sql = "DELETE FROM estudante WHERE cpf = ?";

        Connection conexao = abrirConexao();
        PreparedStatement comando = conexao.prepareStatement(sql);
        comando.setString(1, cpf);
        comando.executeUpdate();

        comando.close();
        fecharConexao(conexao);
    }

    public Estudante buscarPorCpf(String cpf) throws SQLException {
        String sql = "SELECT cpf, nome, email, senha, curso, semestre, modalidade_preferida " +
                     "FROM estudante WHERE cpf = ?";

        Connection conexao = abrirConexao();
        PreparedStatement comando = conexao.prepareStatement(sql);
        comando.setString(1, cpf);
        ResultSet resultado = comando.executeQuery();

        Estudante estudante = null;
        if (resultado.next()) {
            estudante = montarEstudante(resultado);
        }

        resultado.close();
        comando.close();
        fecharConexao(conexao);
        return estudante;
    }

    // Usado no login: busca por e-mail em vez de cpf
    public Estudante buscarPorEmail(String email) throws SQLException {
        String sql = "SELECT cpf, nome, email, senha, curso, semestre, modalidade_preferida " +
                     "FROM estudante WHERE email = ?";

        Connection conexao = abrirConexao();
        PreparedStatement comando = conexao.prepareStatement(sql);
        comando.setString(1, email);
        ResultSet resultado = comando.executeQuery();

        Estudante estudante = null;
        if (resultado.next()) {
            estudante = montarEstudante(resultado);
        }

        resultado.close();
        comando.close();
        fecharConexao(conexao);
        return estudante;
    }

    public List<Estudante> listarTodos() throws SQLException {
        List<Estudante> estudantes = new ArrayList<>();
        String sql = "SELECT cpf, nome, email, senha, curso, semestre, modalidade_preferida " +
                     "FROM estudante ORDER BY nome";

        Connection conexao = abrirConexao();
        PreparedStatement comando = conexao.prepareStatement(sql);
        ResultSet resultado = comando.executeQuery();

        while (resultado.next()) {
            estudantes.add(montarEstudante(resultado));
        }

        resultado.close();
        comando.close();
        fecharConexao(conexao);
        return estudantes;
    }

    //Instancia objeto Estudante a partir do ResultSet
    private Estudante montarEstudante(ResultSet resultado) throws SQLException {
        return new Estudante(
                resultado.getString("cpf"),
                resultado.getString("nome"),
                resultado.getString("email"),
                resultado.getString("senha"),
                resultado.getString("curso"),
                resultado.getInt("semestre"),
                resultado.getString("modalidade_preferida")
        );
    }
}
