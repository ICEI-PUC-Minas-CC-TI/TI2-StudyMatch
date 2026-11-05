package com.studymatch.service;

import java.sql.SQLException;
import com.studymatch.dao.HorarioDAO;
import com.studymatch.model.Horario;
import spark.Request;
import spark.Response;

public class HorarioService {
    private HorarioDAO dao = new HorarioDAO();

    public Object get(Request request, Response response) {
        response.type("application/json");
        try {
            int id = Integer.parseInt(request.params(":id"));
            Horario h = dao.buscarPorId(id);
            return h != null ? JsonUtil.toJson(h) : "{\"erro\": \"Horário não encontrado\"}";
        } catch (SQLException e) {
            response.status(500);
            return "{\"erro\": \"" + e.getMessage() + "\"}";
        }
    }

    public Object getAll(Request request, Response response) {
        response.type("application/json");
        try {
            return JsonUtil.toJson(dao.listarTodos());
        } catch (SQLException e) {
            response.status(500);
            return "{\"erro\": \"" + e.getMessage() + "\"}";
        }
    }
}