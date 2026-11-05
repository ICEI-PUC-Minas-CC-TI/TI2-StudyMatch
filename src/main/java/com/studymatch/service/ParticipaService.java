package com.studymatch.service;

import com.studymatch.dao.GrupoDAO;
import com.studymatch.dao.ParticipaDAO;
import com.studymatch.model.Grupo;
import com.studymatch.model.Participa;
import spark.Request;
import spark.Response;

public class ParticipaService {
    private ParticipaDAO dao = new ParticipaDAO();
    private GrupoDAO grupoDao = new GrupoDAO();

    public Object insert(Request request, Response response) {
        response.type("application/json");
        try {
            String cpf = request.queryParams("cpf");
            int idGrupo = Integer.parseInt(request.queryParams("idGrupo"));

            Grupo grupo = grupoDao.buscarPorId(idGrupo);
            if (grupo == null) throw new IllegalArgumentException("Grupo não encontrado.");
            if (dao.buscar(cpf, idGrupo) != null) throw new IllegalArgumentException("Estudante já participa desse grupo.");
            if (grupoDao.contarParticipantes(idGrupo) >= grupo.getLimiteParticipantes()) {
                throw new IllegalArgumentException("Esse grupo já atingiu o limite de participantes.");
            }

            Participa p = new Participa(cpf, idGrupo);
            dao.inserir(p);
            response.status(201);
            return JsonUtil.toJson(p);
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
            int idGrupo = Integer.parseInt(request.params(":idGrupo"));
            Grupo grupo = grupoDao.buscarPorId(idGrupo);
            if (grupo != null && grupo.getCpfCriador().equals(cpf)) {
                throw new IllegalArgumentException("O criador não pode sair do grupo, apenas excluí-lo.");
            }
            dao.remover(cpf, idGrupo);
            return "{\"mensagem\": \"Saiu do grupo com sucesso.\"}";
        } catch (Exception e) {
            response.status(400);
            return "{\"erro\": \"" + e.getMessage() + "\"}";
        }
    }
}