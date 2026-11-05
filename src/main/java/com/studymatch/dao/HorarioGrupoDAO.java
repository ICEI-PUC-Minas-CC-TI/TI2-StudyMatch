package com.studymatch.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.studymatch.model.HorarioGrupo;

public class HorarioGrupoDAO {

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

    public void inserir(HorarioGrupo hg) throws SQLException {
        String sql = "INSERT INTO horario_grupo (id_grupo, id_horario) VALUES (?, ?)";

        Connection conexao = abrirConexao();
        PreparedStatement comando = conexao.prepareStatement(sql);
        comando.setInt(1, hg.getIdGrupo());
        comando.setInt(2, hg.getIdHorario());
        comando.executeUpdate();

        comando.close();
        fecharConexao(conexao);
    }

    public void remover(int idGrupo, int idHorario) throws SQLException {
        String sql = "DELETE FROM horario_grupo WHERE id_grupo = ? AND id_horario = ?";

        Connection conexao = abrirConexao();
        PreparedStatement comando = conexao.prepareStatement(sql);
        comando.setInt(1, idGrupo);
        comando.setInt(2, idHorario);
        comando.executeUpdate();

        comando.close();
        fecharConexao(conexao);
    }

    public List<HorarioGrupo> listarPorGrupo(int idGrupo) throws SQLException {
        List<HorarioGrupo> lista = new ArrayList<>();
        String sql = "SELECT id_grupo, id_horario FROM horario_grupo WHERE id_grupo = ?";

        Connection conexao = abrirConexao();
        PreparedStatement comando = conexao.prepareStatement(sql);
        comando.setInt(1, idGrupo);
        ResultSet resultado = comando.executeQuery();

        while (resultado.next()) {
            lista.add(new HorarioGrupo(resultado.getInt("id_grupo"), resultado.getInt("id_horario")));
        }

        resultado.close();
        comando.close();
        fecharConexao(conexao);
        return lista;
    }

    public List<HorarioGrupo> listarTodos() throws SQLException {
        List<HorarioGrupo> lista = new ArrayList<>();
        String sql = "SELECT id_grupo, id_horario FROM horario_grupo";

        Connection conexao = abrirConexao();
        PreparedStatement comando = conexao.prepareStatement(sql);
        ResultSet resultado = comando.executeQuery();

        while (resultado.next()) {
            lista.add(new HorarioGrupo(resultado.getInt("id_grupo"), resultado.getInt("id_horario")));
        }

        resultado.close();
        comando.close();
        fecharConexao(conexao);
        return lista;
    }
}
