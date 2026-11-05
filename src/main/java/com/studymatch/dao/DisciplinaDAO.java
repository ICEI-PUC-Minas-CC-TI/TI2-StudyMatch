package com.studymatch.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.studymatch.model.Disciplina;

public class DisciplinaDAO {

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

    //Consulta de registros pré-cadastrados no sistema
    public List<Disciplina> listarTodos() throws SQLException {
        List<Disciplina> disciplinas = new ArrayList<>();
        String sql = "SELECT id_disciplina, codigo, nome FROM disciplina ORDER BY id_disciplina";

        Connection conexao = abrirConexao();
        PreparedStatement comando = conexao.prepareStatement(sql);
        ResultSet resultado = comando.executeQuery();

        while (resultado.next()) {
            int id = resultado.getInt("id_disciplina");
            String codigo = resultado.getString("codigo");
            String nome = resultado.getString("nome");
            disciplinas.add(new Disciplina(id, codigo, nome));
        }

        resultado.close();
        comando.close();
        fecharConexao(conexao);
        return disciplinas;
    }

    public Disciplina buscarPorId(int id) throws SQLException {
        String sql = "SELECT id_disciplina, codigo, nome FROM disciplina WHERE id_disciplina = ?";

        Connection conexao = abrirConexao();
        PreparedStatement comando = conexao.prepareStatement(sql);
        comando.setInt(1, id);
        ResultSet resultado = comando.executeQuery();

        Disciplina disciplina = null;
        if (resultado.next()) {
            String codigo = resultado.getString("codigo");
            String nome = resultado.getString("nome");
            disciplina = new Disciplina(id, codigo, nome);
        }

        resultado.close();
        comando.close();
        fecharConexao(conexao);
        return disciplina;
    }
}
