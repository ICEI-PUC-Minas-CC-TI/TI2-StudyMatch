package com.studymatch.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.studymatch.model.Grupo;

public class GrupoDAO {

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

    //Insere grupo e retorna o ID gerado
    public int inserir(Grupo g) throws SQLException {
        String sql = "INSERT INTO grupo (nome, objetivo, modalidade, descricao, limite_participantes, " +
                     "id_disciplina, cpf_criador) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id_grupo";

        Connection conexao = abrirConexao();
        PreparedStatement comando = conexao.prepareStatement(sql);
        comando.setString(1, g.getNome());
        comando.setString(2, g.getObjetivo());
        comando.setString(3, g.getModalidade());
        comando.setString(4, g.getDescricao());
        comando.setInt(5, g.getLimiteParticipantes());
        comando.setInt(6, g.getIdDisciplina());
        comando.setString(7, g.getCpfCriador());

        ResultSet resultado = comando.executeQuery();
        int idGerado = 0;
        if (resultado.next()) {
            idGerado = resultado.getInt("id_grupo");
        }

        resultado.close();
        comando.close();
        fecharConexao(conexao);
        return idGerado;
    }

    public void atualizar(Grupo g) throws SQLException {
        String sql = "UPDATE grupo SET nome = ?, objetivo = ?, modalidade = ?, descricao = ?, " +
                     "limite_participantes = ?, id_disciplina = ? WHERE id_grupo = ?";

        Connection conexao = abrirConexao();
        PreparedStatement comando = conexao.prepareStatement(sql);
        comando.setString(1, g.getNome());
        comando.setString(2, g.getObjetivo());
        comando.setString(3, g.getModalidade());
        comando.setString(4, g.getDescricao());
        comando.setInt(5, g.getLimiteParticipantes());
        comando.setInt(6, g.getIdDisciplina());
        comando.setInt(7, g.getIdGrupo());
        comando.executeUpdate();

        comando.close();
        fecharConexao(conexao);
    }

    public void remover(int idGrupo) throws SQLException {
        String sql = "DELETE FROM grupo WHERE id_grupo = ?";

        Connection conexao = abrirConexao();
        PreparedStatement comando = conexao.prepareStatement(sql);
        comando.setInt(1, idGrupo);
        comando.executeUpdate();

        comando.close();
        fecharConexao(conexao);
    }

    public Grupo buscarPorId(int idGrupo) throws SQLException {
        String sql = "SELECT id_grupo, nome, objetivo, modalidade, descricao, data_criacao, " +
                     "limite_participantes, id_disciplina, cpf_criador FROM grupo WHERE id_grupo = ?";

        Connection conexao = abrirConexao();
        PreparedStatement comando = conexao.prepareStatement(sql);
        comando.setInt(1, idGrupo);
        ResultSet resultado = comando.executeQuery();

        Grupo grupo = null;
        if (resultado.next()) {
            grupo = montar(resultado);
        }

        resultado.close();
        comando.close();
        fecharConexao(conexao);
        return grupo;
    }

    public List<Grupo> listarTodos() throws SQLException {
        List<Grupo> grupos = new ArrayList<>();
        String sql = "SELECT id_grupo, nome, objetivo, modalidade, descricao, data_criacao, " +
                     "limite_participantes, id_disciplina, cpf_criador FROM grupo ORDER BY data_criacao DESC";

        Connection conexao = abrirConexao();
        PreparedStatement comando = conexao.prepareStatement(sql);
        ResultSet resultado = comando.executeQuery();

        while (resultado.next()) {
            grupos.add(montar(resultado));
        }

        resultado.close();
        comando.close();
        fecharConexao(conexao);
        return grupos;
    }

    //Usado na tela "Buscar Grupos": filtra os grupos de uma disciplina específica
    public List<Grupo> listarPorDisciplina(int idDisciplina) throws SQLException {
        List<Grupo> grupos = new ArrayList<>();
        String sql = "SELECT id_grupo, nome, objetivo, modalidade, descricao, data_criacao, " +
                     "limite_participantes, id_disciplina, cpf_criador FROM grupo WHERE id_disciplina = ?";

        Connection conexao = abrirConexao();
        PreparedStatement comando = conexao.prepareStatement(sql);
        comando.setInt(1, idDisciplina);
        ResultSet resultado = comando.executeQuery();

        while (resultado.next()) {
            grupos.add(montar(resultado));
        }

        resultado.close();
        comando.close();
        fecharConexao(conexao);
        return grupos;
    }

    //Conta quantos estudantes já estão no grupo (usa a tabela participa) — usado para a RN02
    public int contarParticipantes(int idGrupo) throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM participa WHERE id_grupo = ?";

        Connection conexao = abrirConexao();
        PreparedStatement comando = conexao.prepareStatement(sql);
        comando.setInt(1, idGrupo);
        ResultSet resultado = comando.executeQuery();

        int total = 0;
        if (resultado.next()) {
            total = resultado.getInt("total");
        }

        resultado.close();
        comando.close();
        fecharConexao(conexao);
        return total;
    }

    private Grupo montar(ResultSet resultado) throws SQLException {
        LocalDateTime dataCriacao = resultado.getObject("data_criacao", LocalDateTime.class);
        return new Grupo(
                resultado.getInt("id_grupo"),
                resultado.getString("nome"),
                resultado.getString("objetivo"),
                resultado.getString("modalidade"),
                resultado.getString("descricao"),
                dataCriacao,
                resultado.getInt("limite_participantes"),
                resultado.getInt("id_disciplina"),
                resultado.getString("cpf_criador")
        );
    }
}
