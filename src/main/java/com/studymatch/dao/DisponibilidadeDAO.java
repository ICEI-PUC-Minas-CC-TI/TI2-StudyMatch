package com.studymatch.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.studymatch.model.Disponibilidade;

public class DisponibilidadeDAO {

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

    public void inserir(Disponibilidade d) throws SQLException {
        String sql = "INSERT INTO disponibilidade (cpf, id_horario) VALUES (?, ?)";

        Connection conexao = abrirConexao();
        PreparedStatement comando = conexao.prepareStatement(sql);
        comando.setString(1, d.getCpf());
        comando.setInt(2, d.getIdHorario());
        comando.executeUpdate();

        comando.close();
        fecharConexao(conexao);
    }

    public void remover(String cpf, int idHorario) throws SQLException {
        String sql = "DELETE FROM disponibilidade WHERE cpf = ? AND id_horario = ?";

        Connection conexao = abrirConexao();
        PreparedStatement comando = conexao.prepareStatement(sql);
        comando.setString(1, cpf);
        comando.setInt(2, idHorario);
        comando.executeUpdate();

        comando.close();
        fecharConexao(conexao);
    }

    public Disponibilidade buscar(String cpf, int idHorario) throws SQLException {
        String sql = "SELECT cpf, id_horario FROM disponibilidade WHERE cpf = ? AND id_horario = ?";

        Connection conexao = abrirConexao();
        PreparedStatement comando = conexao.prepareStatement(sql);
        comando.setString(1, cpf);
        comando.setInt(2, idHorario);
        ResultSet resultado = comando.executeQuery();

        Disponibilidade d = null;
        if (resultado.next()) {
            d = new Disponibilidade(resultado.getString("cpf"), resultado.getInt("id_horario"));
        }

        resultado.close();
        comando.close();
        fecharConexao(conexao);
        return d;
    }

    public List<Disponibilidade> listarPorEstudante(String cpf) throws SQLException {
        List<Disponibilidade> lista = new ArrayList<>();
        String sql = "SELECT cpf, id_horario FROM disponibilidade WHERE cpf = ?";

        Connection conexao = abrirConexao();
        PreparedStatement comando = conexao.prepareStatement(sql);
        comando.setString(1, cpf);
        ResultSet resultado = comando.executeQuery();

        while (resultado.next()) {
            lista.add(new Disponibilidade(resultado.getString("cpf"), resultado.getInt("id_horario")));
        }

        resultado.close();
        comando.close();
        fecharConexao(conexao);
        return lista;
    }

    public List<Disponibilidade> listarTodos() throws SQLException {
        List<Disponibilidade> lista = new ArrayList<>();
        String sql = "SELECT cpf, id_horario FROM disponibilidade";

        Connection conexao = abrirConexao();
        PreparedStatement comando = conexao.prepareStatement(sql);
        ResultSet resultado = comando.executeQuery();

        while (resultado.next()) {
            lista.add(new Disponibilidade(resultado.getString("cpf"), resultado.getInt("id_horario")));
        }

        resultado.close();
        comando.close();
        fecharConexao(conexao);
        return lista;
    }
}
