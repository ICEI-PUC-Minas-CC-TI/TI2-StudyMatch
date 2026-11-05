package com.studymatch.service;

import java.util.Arrays;
import java.util.List;
import com.studymatch.dao.MatriculaDAO;
import com.studymatch.model.Matricula;
import spark.Request;
import spark.Response;

public class MatriculaService {
    private MatriculaDAO dao = new MatriculaDAO();
    private static final List<String> NIVEIS_VALIDOS = Arrays.asList("basico", "intermediario", "avancado");
    private static final List<String> OBJETIVOS_VALIDOS = Arrays.asList("preparar_prova", "reforcar_base", "aprofundar", "trabalhos");

    public Object insert(Request request, Response response) {
        response.type("application/json");
        try {
            String cpf = request.queryParams("cpf");
            int idDisciplina = Integer.parseInt(request.queryParams("idDisciplina"));
            String nivel = request.queryParams("nivelConhecimento");
            String objetivo = request.queryParams("objetivo");

            Matricula m = new Matricula(cpf, idDisciplina, nivel, objetivo);
            if (!NIVEIS_VALIDOS.contains(m.getNivelConhecimento()) || !OBJETIVOS_VALIDOS.contains(m.getObjetivo())) {
                throw new IllegalArgumentException("Nível ou objetivo inválido.");
            }
            if (dao.buscar(cpf, idDisciplina) != null) {
                dao.atualizar(m);
            } else {
                dao.inserir(m);
            }
            response.status(201);
            return JsonUtil.toJson(m);
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

    public Object delete(Request request, Response response) {
        response.type("application/json");
        try {
            String cpf = request.params(":cpf");
            int idDisciplina = Integer.parseInt(request.params(":idDisciplina"));
            dao.remover(cpf, idDisciplina);
            return "{\"mensagem\": \"Matrícula removida.\"}";
        } catch (Exception e) {
            response.status(400);
            return "{\"erro\": \"" + e.getMessage() + "\"}";
        }
    }
}