package com.studymatch.service;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import com.google.gson.Gson;
import com.studymatch.dao.GrupoDAO;
import com.studymatch.dao.ParticipaDAO;
import com.studymatch.model.Grupo;
import com.studymatch.model.Participa;
import spark.Request;
import spark.Response;

public class GrupoService {

    private GrupoDAO dao = new GrupoDAO();
    private ParticipaDAO participaDao = new ParticipaDAO();
    private Gson gson = new Gson();

    private static final List<String> MODALIDADES_VALIDAS = Arrays.asList("presencial", "online", "hibrido");
    private static final List<String> OBJETIVOS_VALIDOS = Arrays.asList("preparar_prova", "reforcar_base", "aprofundar", "trabalhos");

    public Object insert(Request request, Response response) {
        response.type("application/json");
        try {
            String nome = request.queryParams("nome");
            String objetivo = request.queryParams("objetivo");
            String modalidade = request.queryParams("modalidade");
            String descricao = request.queryParams("descricao");
            int limite = Integer.parseInt(request.queryParams("limiteParticipantes"));
            int idDisciplina = Integer.parseInt(request.queryParams("idDisciplina"));
            String cpfCriador = request.queryParams("cpfCriador");

            Grupo g = new Grupo(nome, objetivo, modalidade, descricao, limite, idDisciplina, cpfCriador);
            validar(g);

            int idGerado = dao.inserir(g);
            participaDao.inserir(new Participa(cpfCriador, idGerado));

            response.status(201);
            return "{\"idGrupo\": " + idGerado + ", \"mensagem\": \"Grupo criado com sucesso!\"}";
        } catch (Exception e) {
            response.status(400);
            return "{\"erro\": \"" + e.getMessage() + "\"}";
        }
    }

    public Object get(Request request, Response response) {
        response.type("application/json");
        try {
            int id = Integer.parseInt(request.params(":id"));
            Grupo g = dao.buscarPorId(id);
            if (g != null) return JsonUtil.toJson(g);
            response.status(404);
            return "{\"erro\": \"Grupo não encontrado.\"}";
        } catch (Exception e) {
            response.status(500);
            return "{\"erro\": \"" + e.getMessage() + "\"}";
        }
    }

    public Object getAll(Request request, Response response) {
        response.type("application/json");
        try {
            List<Grupo> lista = dao.listarTodos();
            return JsonUtil.toJson(lista);
        } catch (SQLException e) {
            response.status(500);
            return "{\"erro\": \"" + e.getMessage() + "\"}";
        }
    }

    public Object update(Request request, Response response) {
        response.type("application/json");
        try {
            int id = Integer.parseInt(request.params(":id"));
            Grupo g = new Grupo(
                id,
                request.queryParams("nome"),
                request.queryParams("objetivo"),
                request.queryParams("modalidade"),
                request.queryParams("descricao"),
                null,
                Integer.parseInt(request.queryParams("limiteParticipantes")),
                Integer.parseInt(request.queryParams("idDisciplina")),
                request.queryParams("cpfCriador")
            );
            validar(g);
            dao.atualizar(g);
            return JsonUtil.toJson(g);
        } catch (Exception e) {
            response.status(400);
            return "{\"erro\": \"" + e.getMessage() + "\"}";
        }
    }

    public Object delete(Request request, Response response) {
        response.type("application/json");
        try {
            int id = Integer.parseInt(request.params(":id"));
            dao.remover(id);
            return "{\"mensagem\": \"Grupo excluído com sucesso.\"}";
        } catch (SQLException e) {
            response.status(500);
            return "{\"erro\": \"" + e.getMessage() + "\"}";
        }
    }

    private void validar(Grupo g) {
        if (g.getNome() == null || g.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome do grupo é obrigatório.");
        }
        if (!MODALIDADES_VALIDAS.contains(g.getModalidade())) {
            throw new IllegalArgumentException("Modalidade inválida.");
        }
        if (!OBJETIVOS_VALIDOS.contains(g.getObjetivo())) {
            throw new IllegalArgumentException("Objetivo inválido.");
        }
        if (g.getLimiteParticipantes() < 2) {
            throw new IllegalArgumentException("O limite de participantes deve ser pelo menos 2.");
        }
    }
}