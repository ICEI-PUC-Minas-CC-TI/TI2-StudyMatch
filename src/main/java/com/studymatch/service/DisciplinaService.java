package com.studymatch.service;

import java.sql.SQLException;
import java.util.List;
import com.google.gson.Gson;
import com.studymatch.dao.DisciplinaDAO;
import com.studymatch.model.Disciplina;
import spark.Request;
import spark.Response;

public class DisciplinaService {

    private DisciplinaDAO dao = new DisciplinaDAO();
    private Gson gson = new Gson();

    public Object get(Request request, Response response) {
        response.type("application/json");
        try {
            int id = Integer.parseInt(request.params(":id"));
            Disciplina d = dao.buscarPorId(id);
            return d != null ? gson.toJson(d) : "{\"erro\": \"Não encontrado\"}";
        } catch (SQLException e) {
            response.status(500);
            return "{\"erro\": \"" + e.getMessage() + "\"}";
        }
    }

    public Object getAll(Request request, Response response) {
        response.type("application/json");
        try {
            List<Disciplina> lista = dao.listarTodos();
            return gson.toJson(lista);
        } catch (SQLException e) {
            response.status(500);
            return "{\"erro\": \"" + e.getMessage() + "\"}";
        }
    }
}