package com.studymatch.service;

import com.studymatch.dao.HorarioGrupoDAO;
import com.studymatch.model.HorarioGrupo;
import spark.Request;
import spark.Response;

public class HorarioGrupoService {
    private HorarioGrupoDAO dao = new HorarioGrupoDAO();

    public Object insert(Request request, Response response) {
        response.type("application/json");
        try {
            int idGrupo = Integer.parseInt(request.queryParams("idGrupo"));
            int idHorario = Integer.parseInt(request.queryParams("idHorario"));
            HorarioGrupo hg = new HorarioGrupo(idGrupo, idHorario);
            dao.inserir(hg);
            response.status(201);
            return JsonUtil.toJson(hg);
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