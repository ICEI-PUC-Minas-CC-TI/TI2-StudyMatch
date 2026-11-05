package com.studymatch.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.studymatch.model.PlanoEstudo;

public class PlanoEstudoDAO {

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

    //Insere plano de estudos no banco
    public int inserir(PlanoEstudo p) throws SQLException {
        String sql = "INSERT INTO plano_estudo (cpf, id_disciplina, data_prova, tempo_disponivel, " +
                     "dificuldades, conteudo) VALUES (?, ?, ?, ?, ?, ?) RETURNING id_plano";

        Connection conexao = abrirConexao();
        PreparedStatement comando = conexao.prepareStatement(sql);
        comando.setString(1, p.getCpf());
        comando.setInt(2, p.getIdDisciplina());
        comando.setDate(3, Date.valueOf(p.getDataProva()));
        comando.setString(4, p.getTempoDisponivel());
        comando.setString(5, p.getDificuldades());
        comando.setString(6, p.getConteudo());

        ResultSet resultado = comando.executeQuery();
        int idGerado = 0;
        if (resultado.next()) {
            idGerado = resultado.getInt("id_plano");
        }

        resultado.close();
        comando.close();
        fecharConexao(conexao);
        return idGerado;
    }

    public PlanoEstudo buscarPorId(int idPlano) throws SQLException {
        String sql = "SELECT id_plano, cpf, id_disciplina, data_criacao, data_prova, tempo_disponivel, " +
                     "dificuldades, conteudo FROM plano_estudo WHERE id_plano = ?";

        Connection conexao = abrirConexao();
        PreparedStatement comando = conexao.prepareStatement(sql);
        comando.setInt(1, idPlano);
        ResultSet resultado = comando.executeQuery();

        PlanoEstudo plano = null;
        if (resultado.next()) {
            plano = montar(resultado);
        }

        resultado.close();
        comando.close();
        fecharConexao(conexao);
        return plano;
    }

    public List<PlanoEstudo> listarPorEstudante(String cpf) throws SQLException {
        List<PlanoEstudo> planos = new ArrayList<>();
        String sql = "SELECT id_plano, cpf, id_disciplina, data_criacao, data_prova, tempo_disponivel, " +
                     "dificuldades, conteudo FROM plano_estudo WHERE cpf = ? ORDER BY data_criacao DESC";

        Connection conexao = abrirConexao();
        PreparedStatement comando = conexao.prepareStatement(sql);
        comando.setString(1, cpf);
        ResultSet resultado = comando.executeQuery();

        while (resultado.next()) {
            planos.add(montar(resultado));
        }

        resultado.close();
        comando.close();
        fecharConexao(conexao);
        return planos;
    }

    public List<PlanoEstudo> listarTodos() throws SQLException {
        List<PlanoEstudo> planos = new ArrayList<>();
        String sql = "SELECT id_plano, cpf, id_disciplina, data_criacao, data_prova, tempo_disponivel, " +
                     "dificuldades, conteudo FROM plano_estudo ORDER BY data_criacao DESC";

        Connection conexao = abrirConexao();
        PreparedStatement comando = conexao.prepareStatement(sql);
        ResultSet resultado = comando.executeQuery();

        while (resultado.next()) {
            planos.add(montar(resultado));
        }

        resultado.close();
        comando.close();
        fecharConexao(conexao);
        return planos;
    }

    private PlanoEstudo montar(ResultSet resultado) throws SQLException {
        LocalDateTime dataCriacao = resultado.getObject("data_criacao", LocalDateTime.class);
        LocalDate dataProva = resultado.getObject("data_prova", LocalDate.class);
        return new PlanoEstudo(
                resultado.getInt("id_plano"),
                resultado.getString("cpf"),
                resultado.getInt("id_disciplina"),
                dataCriacao,
                dataProva,
                resultado.getString("tempo_disponivel"),
                resultado.getString("dificuldades"),
                resultado.getString("conteudo")
        );
    }
}
