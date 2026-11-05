package com.studymatch.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import com.studymatch.model.Horario;

public class HorarioDAO {

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
    public List<Horario> listarTodos() throws SQLException {
        List<Horario> horarios = new ArrayList<>();
        String sql = "SELECT id_horario, dia_semana, hora_inicio, hora_fim FROM horario ORDER BY id_horario";

        Connection conexao = abrirConexao();
        PreparedStatement comando = conexao.prepareStatement(sql);
        ResultSet resultado = comando.executeQuery();

        while (resultado.next()) {
            int id = resultado.getInt("id_horario");
            String diaSemana = resultado.getString("dia_semana");
            LocalTime horaInicio = resultado.getObject("hora_inicio", LocalTime.class);
            LocalTime horaFim = resultado.getObject("hora_fim", LocalTime.class);
            horarios.add(new Horario(id, diaSemana, horaInicio, horaFim));
        }

        resultado.close();
        comando.close();
        fecharConexao(conexao);
        return horarios;
    }

    public Horario buscarPorId(int id) throws SQLException {
        String sql = "SELECT id_horario, dia_semana, hora_inicio, hora_fim FROM horario WHERE id_horario = ?";

        Connection conexao = abrirConexao();
        PreparedStatement comando = conexao.prepareStatement(sql);
        comando.setInt(1, id);
        ResultSet resultado = comando.executeQuery();

        Horario horario = null;
        if (resultado.next()) {
            String diaSemana = resultado.getString("dia_semana");
            LocalTime horaInicio = resultado.getObject("hora_inicio", LocalTime.class);
            LocalTime horaFim = resultado.getObject("hora_fim", LocalTime.class);
            horario = new Horario(id, diaSemana, horaInicio, horaFim);
        }

        resultado.close();
        comando.close();
        fecharConexao(conexao);
        return horario;
    }
}
