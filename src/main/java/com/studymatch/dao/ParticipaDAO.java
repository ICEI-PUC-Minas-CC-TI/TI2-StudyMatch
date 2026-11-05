package com.studymatch.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.studymatch.model.Participa;

public class ParticipaDAO {

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

    // data_entrada não é informada aqui: o banco preenche sozinho com DEFAULT CURRENT_TIMESTAMP
    public void inserir(Participa p) throws SQLException {
        String sql = "INSERT INTO participa (cpf, id_grupo) VALUES (?, ?)";

        Connection conexao = abrirConexao();
        PreparedStatement comando = conexao.prepareStatement(sql);
        comando.setString(1, p.getCpf());
        comando.setInt(2, p.getIdGrupo());
        comando.executeUpdate();

        comando.close();
        fecharConexao(conexao);
    }

    public void remover(String cpf, int idGrupo) throws SQLException {
        String sql = "DELETE FROM participa WHERE cpf = ? AND id_grupo = ?";

        Connection conexao = abrirConexao();
        PreparedStatement comando = conexao.prepareStatement(sql);
        comando.setString(1, cpf);
        comando.setInt(2, idGrupo);
        comando.executeUpdate();

        comando.close();
        fecharConexao(conexao);
    }

    public Participa buscar(String cpf, int idGrupo) throws SQLException {
        String sql = "SELECT cpf, id_grupo, data_entrada FROM participa WHERE cpf = ? AND id_grupo = ?";

        Connection conexao = abrirConexao();
        PreparedStatement comando = conexao.prepareStatement(sql);
        comando.setString(1, cpf);
        comando.setInt(2, idGrupo);
        ResultSet resultado = comando.executeQuery();

        Participa p = null;
        if (resultado.next()) {
            p = montar(resultado);
        }

        resultado.close();
        comando.close();
        fecharConexao(conexao);
        return p;
    }

    public List<Participa> listarPorGrupo(int idGrupo) throws SQLException {
        List<Participa> lista = new ArrayList<>();
        String sql = "SELECT cpf, id_grupo, data_entrada FROM participa WHERE id_grupo = ?";

        Connection conexao = abrirConexao();
        PreparedStatement comando = conexao.prepareStatement(sql);
        comando.setInt(1, idGrupo);
        ResultSet resultado = comando.executeQuery();

        while (resultado.next()) {
            lista.add(montar(resultado));
        }

        resultado.close();
        comando.close();
        fecharConexao(conexao);
        return lista;
    }

    public List<Participa> listarPorEstudante(String cpf) throws SQLException {
        List<Participa> lista = new ArrayList<>();
        String sql = "SELECT cpf, id_grupo, data_entrada FROM participa WHERE cpf = ?";

        Connection conexao = abrirConexao();
        PreparedStatement comando = conexao.prepareStatement(sql);
        comando.setString(1, cpf);
        ResultSet resultado = comando.executeQuery();

        while (resultado.next()) {
            lista.add(montar(resultado));
        }

        resultado.close();
        comando.close();
        fecharConexao(conexao);
        return lista;
    }

    public List<Participa> listarTodos() throws SQLException {
        List<Participa> lista = new ArrayList<>();
        String sql = "SELECT cpf, id_grupo, data_entrada FROM participa";

        Connection conexao = abrirConexao();
        PreparedStatement comando = conexao.prepareStatement(sql);
        ResultSet resultado = comando.executeQuery();

        while (resultado.next()) {
            lista.add(montar(resultado));
        }

        resultado.close();
        comando.close();
        fecharConexao(conexao);
        return lista;
    }

    private Participa montar(ResultSet resultado) throws SQLException {
        LocalDateTime dataEntrada = resultado.getObject("data_entrada", LocalDateTime.class);
        return new Participa(resultado.getString("cpf"), resultado.getInt("id_grupo"), dataEntrada);
    }
}
