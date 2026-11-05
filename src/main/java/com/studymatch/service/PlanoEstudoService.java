package com.studymatch.service;

import java.time.LocalDate;
import com.studymatch.dao.PlanoEstudoDAO;
import com.studymatch.model.PlanoEstudo;
import spark.Request;
import spark.Response;

public class PlanoEstudoService {

    private PlanoEstudoDAO dao = new PlanoEstudoDAO();

    public Object insert(Request request, Response response) {
        response.type("application/json");
        try {
            String cpf = request.queryParams("cpf");
            int idDisciplina = Integer.parseInt(request.queryParams("idDisciplina"));
            LocalDate dataProva = LocalDate.parse(request.queryParams("dataProva"));
            String tempo = request.queryParams("tempoDisponivel");
            String dificuldades = request.queryParams("dificuldades");
            String conteudo = request.queryParams("conteudo");
            
            if (conteudo == null || conteudo.isBlank()) {
                conteudo = "Plano gerado para focar em: " + dificuldades;
            }

            PlanoEstudo p = new PlanoEstudo(cpf, idDisciplina, dataProva, tempo, dificuldades, conteudo);
            int id = dao.inserir(p);
            p.setIdPlano(id);
            
            response.status(201);
            return JsonUtil.toJson(p);
        } catch (Exception e) {
            response.status(400);
            return "{\"erro\": \"" + e.getMessage() + "\"}";
        }
    }

    public Object get(Request request, Response response) {
        response.type("application/json");
        try {
            int id = Integer.parseInt(request.params(":id"));
            PlanoEstudo p = dao.buscarPorId(id);
            return p != null ? JsonUtil.toJson(p) : "{\"erro\": \"Plano não encontrado.\"}";
        } catch (Exception e) {
            response.status(500);
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