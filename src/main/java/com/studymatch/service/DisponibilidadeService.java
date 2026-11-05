package com.studymatch.service;

import com.studymatch.dao.DisponibilidadeDAO;
import com.studymatch.model.Disponibilidade;
import spark.Request;
import spark.Response;

public class DisponibilidadeService {
    private DisponibilidadeDAO dao = new DisponibilidadeDAO();

    public Object insert(Request request, Response response) {
        response.type("application/json");
        try {
            String cpf = request.queryParams("cpf");
            int idHorario = Integer.parseInt(request.queryParams("idHorario"));
            Disponibilidade d = new Disponibilidade(cpf, idHorario);
            if (dao.buscar(cpf, idHorario) == null) {
                dao.inserir(d);
            }
            response.status(201);
            return JsonUtil.toJson(d);
        } catch (Exception e) {
            response.status(400);
            return "{\"erro\": \"" + e.getMessage() + "\"}";
        }
    }

    public Object getAll(Request request, Response response) {
        response.type("application/json");
        try {
            return JsonUtil.toJson(dao.listarTodos());
        } catch (Exception e) {
            response.status(500);
            return "{\"erro\": \"" + e.getMessage() + "\"}";
        }
    }
}